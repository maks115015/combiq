package ru.atott.combiq.service.question.impl;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.global.GlobalBuilder;
import org.elasticsearch.search.aggregations.bucket.global.InternalGlobal;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.DefaultResultMapper;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;
import org.springframework.stereotype.Service;
import ru.atott.combiq.dao.Types;
import ru.atott.combiq.dao.entity.QuestionAttrsEntity;
import ru.atott.combiq.dao.entity.QuestionEntity;
import ru.atott.combiq.dao.es.NameVersionDomainResolver;
import ru.atott.combiq.dao.repository.QuestionAttrsRepository;
import ru.atott.combiq.service.bean.QuestionAttrs;
import ru.atott.combiq.service.bean.Tag;
import ru.atott.combiq.service.dsl.DslQuery;
import ru.atott.combiq.service.mapper.QuestionAttrsEntityMapper;
import ru.atott.combiq.service.mapper.QuestionEntityMapper;
import ru.atott.combiq.service.question.GetQuestionService;
import ru.atott.combiq.service.question.SearchContext;
import ru.atott.combiq.service.question.SearchResponse;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GetQuestionServiceImpl implements GetQuestionService {
    private DefaultResultMapper defaultResultMapper;
    private QuestionAttrsEntityMapper questionAttrsEntityMapper = new QuestionAttrsEntityMapper();
    @Autowired
    private NameVersionDomainResolver domainResolver;
    @Autowired(required = false)
    private Client client;
    @Autowired
    private QuestionAttrsRepository questionAttrsRepository;

    public GetQuestionServiceImpl() {
        SimpleElasticsearchMappingContext mappingContext = new SimpleElasticsearchMappingContext();
        defaultResultMapper = new DefaultResultMapper(mappingContext);
    }

    @Override
    public SearchResponse getQuestions(SearchContext context) {
        DslQuery dsl = context.getDslQuery();

        SearchRequestBuilder query = client
                .prepareSearch(domainResolver.resolveQuestionIndex())
                .setTypes(Types.question)
                .setQuery(QueryBuilders.filteredQuery(getQueryBuilder(dsl), getFilterBuilder(dsl)))
                .addSort("reputation", SortOrder.DESC)
                .setFrom(context.getPage() * context.getSize())
                .setSize(context.getSize());

        getAggregationBuilders(dsl).stream().forEach(query::addAggregation);

        org.elasticsearch.action.search.SearchResponse searchResponse = query.execute().actionGet();

        Pageable pageable = new PageRequest(context.getPage(), context.getSize());
        Page<QuestionEntity> page = defaultResultMapper.mapResults(searchResponse, QuestionEntity.class, pageable);

        QuestionEntityMapper questionMapper = new QuestionEntityMapper();
        if (context.getUserId() != null) {
            Set<String> questionIds = page.getContent().stream().map(QuestionEntity::getId).collect(Collectors.toSet());
            List<QuestionAttrs> questionAttrses = getQuestionAttrses(questionIds, context.getUserId());
            Map<String, QuestionAttrs> attrsMap = questionAttrses.stream().collect(Collectors.toMap(QuestionAttrs::getQuestionId, attrs -> attrs));
            questionMapper = new QuestionEntityMapper(context.getUserId(), attrsMap);
        }

        SearchResponse response = new SearchResponse();
        response.setQuestions(page.map(questionMapper::map));
        response.setPopularTags(getPopularTags(searchResponse));
        return response;
    }

    private List<QuestionAttrs> getQuestionAttrses(Collection<String> questionIds, String userId) {
        OrFilterBuilder filterBuilder = FilterBuilders.orFilter();
        questionIds.forEach(questionId -> {
            filterBuilder.add(FilterBuilders.andFilter(
                    FilterBuilders.termFilter("questionId", questionId),
                    FilterBuilders.termFilter("userId", userId)));
        });
        QueryBuilder queryBuilder = QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), filterBuilder);
        Iterable<QuestionAttrsEntity> questionAttrsEntities = questionAttrsRepository.search(queryBuilder);;
        return questionAttrsEntityMapper.toList(questionAttrsEntities);
    }

    private List<AggregationBuilder> getAggregationBuilders(DslQuery dsl) {
        GlobalBuilder global = AggregationBuilders.global("global");

        TermsBuilder popularTags = AggregationBuilders
                .terms("popularTags")
                .field("tags")
                .size(10);

        global.subAggregation(popularTags);

        return Lists.newArrayList(global);
    }

    private QueryBuilder getQueryBuilder(DslQuery dsl) {
        QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
        if (!dsl.getTerms().isEmpty()) {
            BoolQueryBuilder termsQueryBuilder = QueryBuilders.boolQuery();
            dsl.getTerms().forEach(term -> {
                termsQueryBuilder.must(QueryBuilders.matchQuery("title", term.getValue()));
            });
            queryBuilder = termsQueryBuilder;
        }
        return queryBuilder;
    }

    private FilterBuilder getFilterBuilder(DslQuery dsl) {
        List<FilterBuilder> filters = new ArrayList<>();

        if (!dsl.getTags().isEmpty()) {
            BoolFilterBuilder tagsFilter = FilterBuilders.boolFilter();
            dsl.getTags().forEach(tag -> {
                tagsFilter.must(FilterBuilders.termFilter("tags", tag.getValue()));
            });
            filters.add(tagsFilter);
        }

        if (StringUtils.isNoneBlank(dsl.getLevel())) {
            long level = NumberUtils.toLong(dsl.getLevel().substring(1), -1);
            filters.add(FilterBuilders.termFilter("level", level));
        }

        FilterBuilder filterBuilder = FilterBuilders.matchAllFilter();
        if (!filters.isEmpty()) {
            filterBuilder = FilterBuilders.boolFilter().must(filters.toArray(new FilterBuilder[filters.size()]));
        }

        return filterBuilder;
    }

    private List<Tag> getPopularTags(org.elasticsearch.action.search.SearchResponse response) {
        InternalGlobal global = response.getAggregations().get("global");

        StringTerms popularTagsAgg = global.getAggregations().get("popularTags");
        return popularTagsAgg.getBuckets().stream()
                .map(bucket -> {
                    String tagValue = bucket.getKey();
                    long count = bucket.getDocCount();
                    return new Tag(tagValue, count);
                })
                .collect(Collectors.toList());
    }
}
