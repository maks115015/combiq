package ru.atott.combiq.service.question.impl;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
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
import ru.atott.combiq.service.bean.Question;
import ru.atott.combiq.service.bean.QuestionAttrs;
import ru.atott.combiq.service.bean.Tag;
import ru.atott.combiq.service.dsl.DslQuery;
import ru.atott.combiq.service.mapper.QuestionAttrsMapper;
import ru.atott.combiq.service.mapper.QuestionMapper;
import ru.atott.combiq.service.question.GetQuestionService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GetQuestionServiceImpl implements GetQuestionService {
    private DefaultResultMapper defaultResultMapper;
    private QuestionAttrsMapper questionAttrsMapper = new QuestionAttrsMapper();
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
                .setQuery(QueryBuilders.filteredQuery(getQueryBuilder(dsl), getFilterBuilder(dsl, context.getQuestionIds())))
                .addSort("reputation", SortOrder.DESC)
                .setFrom(context.getFrom())
                .setSize(context.getSize());

        getAggregationBuilders(dsl).stream().forEach(query::addAggregation);

        org.elasticsearch.action.search.SearchResponse searchResponse = query.execute().actionGet();

        Pageable pageable = new PageRequest((int)Math.floor((double)context.getFrom() / (double)context.getSize()), context.getSize());
        Page<QuestionEntity> page = defaultResultMapper.mapResults(searchResponse, QuestionEntity.class, pageable);

        QuestionMapper questionMapper = new QuestionMapper();
        if (context.getUserId() != null) {
            Set<String> questionIds = page.getContent().stream().map(QuestionEntity::getId).collect(Collectors.toSet());
            List<QuestionAttrs> questionAttrses = getQuestionAttrses(questionIds, context.getUserId());
            Map<String, QuestionAttrs> attrsMap = questionAttrses.stream().collect(Collectors.toMap(QuestionAttrs::getQuestionId, attrs -> attrs));
            questionMapper = new QuestionMapper(context.getUserId(), attrsMap);
        }

        SearchResponse response = new SearchResponse();
        response.setQuestions(page.map(questionMapper::map));
        response.setPopularTags(getPopularTags(searchResponse));
        return response;
    }

    @Override
    public GetQuestionResponse getQuestion(GetQuestionContext context) {
        GetQuestionResponse response = new GetQuestionResponse();

        if (context.getDsl() != null && context.getProposedIndexInDslResponse() != null) {
            SearchContext searchContext = new SearchContext();
            if (context.getProposedIndexInDslResponse() == 0) {
                searchContext.setFrom(0);
                searchContext.setSize(2);
            } else {
                searchContext.setFrom(context.getProposedIndexInDslResponse() - 1);
                searchContext.setSize(3);
            }
            searchContext.setDslQuery(context.getDsl());
            searchContext.setUserId(context.getUserId());

            SearchResponse searchResponse = getQuestions(searchContext);
            List<Question> questions = searchResponse.getQuestions().getContent();
            if (context.getProposedIndexInDslResponse() == 0) {
                if (questions.size() > 0 && questions.get(0).getId().equals(context.getId())) {
                    response.setQuestion(questions.get(0));
                    QuestionPositionInDsl positionInDsl = new QuestionPositionInDsl();
                    positionInDsl.setIndex(context.getProposedIndexInDslResponse());
                    positionInDsl.setTotal(searchResponse.getQuestions().getTotalElements());
                    if (questions.size() > 1) {
                        positionInDsl.setNextQuestionId(questions.get(1).getId());
                    }
                    response.setPositionInDsl(positionInDsl);
                }
            } else {
                if (questions.size() > 1 && questions.get(1).getId().equals(context.getId())) {
                    response.setQuestion(questions.get(1));
                    QuestionPositionInDsl positionInDsl = new QuestionPositionInDsl();
                    positionInDsl.setIndex(context.getProposedIndexInDslResponse());
                    positionInDsl.setTotal(searchResponse.getQuestions().getTotalElements());
                    positionInDsl.setPreviosQuestionId(questions.get(0).getId());
                    if (questions.size() > 2) {
                        positionInDsl.setNextQuestionId(questions.get(2).getId());
                    }
                    response.setPositionInDsl(positionInDsl);
                }
            }
        }

        if (response.getQuestion() == null) {
            SearchContext searchContext = new SearchContext();
            searchContext.setFrom(0);
            searchContext.setSize(1);
            searchContext.setUserId(context.getUserId());
            searchContext.setQuestionId(context.getId());
            SearchResponse searchResponse = getQuestions(searchContext);
            if (searchResponse.getQuestions().getContent().size() > 0) {
                response.setQuestion(searchResponse.getQuestions().getContent().get(0));
            }
        }

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
        return questionAttrsMapper.toList(questionAttrsEntities);
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
        if (dsl != null && !dsl.getTerms().isEmpty()) {
            BoolQueryBuilder termsQueryBuilder = QueryBuilders.boolQuery();
            dsl.getTerms().forEach(term -> {
                termsQueryBuilder.must(QueryBuilders.matchQuery("title", term.getValue()));
            });
            queryBuilder = termsQueryBuilder;
        }
        return queryBuilder;
    }

    private FilterBuilder getFilterBuilder(DslQuery dsl, List<String> questionIds) {
        List<FilterBuilder> filters = new ArrayList<>();

        if (dsl != null && !dsl.getTags().isEmpty()) {
            BoolFilterBuilder tagsFilter = FilterBuilders.boolFilter();
            dsl.getTags().forEach(tag -> {
                tagsFilter.must(FilterBuilders.termFilter("tags", tag.getValue()));
            });
            filters.add(tagsFilter);
        }

        if (dsl != null && StringUtils.isNoneBlank(dsl.getLevel())) {
            long level = NumberUtils.toLong(dsl.getLevel().substring(1), -1);
            filters.add(FilterBuilders.termFilter("level", level));
        }

        if (CollectionUtils.isNotEmpty(questionIds)) {
            filters.add(FilterBuilders.idsFilter(Types.question).ids(questionIds.toArray(new String[questionIds.size()])));
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
