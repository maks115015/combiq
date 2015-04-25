package ru.atott.combiq.service.question;

import com.google.common.collect.Lists;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.global.GlobalBuilder;
import org.elasticsearch.search.aggregations.bucket.global.InternalGlobal;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.DefaultResultMapper;
import org.springframework.stereotype.Service;
import ru.atott.combiq.dao.Types;
import ru.atott.combiq.dao.entity.QuestionEntity;
import ru.atott.combiq.dao.es.NameVersionDomainResolver;
import ru.atott.combiq.service.bean.Tag;
import ru.atott.combiq.service.dsl.DslQuery;
import ru.atott.combiq.service.mapper.QuestionEntityToQuestionMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetQuestionServiceImpl implements GetQuestionService {
    private QuestionEntityToQuestionMapper questionMapper = new QuestionEntityToQuestionMapper();
    private DefaultResultMapper defaultResultMapper = new DefaultResultMapper();
    @Autowired
    private NameVersionDomainResolver domainResolver;
    @Autowired(required = false)
    private Client client;

    @Override
    public GetQuestionResponse getQuestions(GetQuestionContext context) {
        DslQuery dsl = context.getDslQuery();

        SearchRequestBuilder query = client
                .prepareSearch(domainResolver.resolveQuestionIndex())
                .setTypes(Types.question)
                .setQuery(QueryBuilders.filteredQuery(getQueryBuilder(dsl), getFilterBuilder(dsl)))
                .setFrom(context.getPage() * context.getSize())
                .setSize(context.getSize());

        getAggregationBuilders(dsl).stream().forEach(query::addAggregation);

        SearchResponse searchResponse = query.execute().actionGet();

        Pageable pageable = new PageRequest(context.getPage(), context.getSize());
        Page<QuestionEntity> page = defaultResultMapper.mapResults(searchResponse, QuestionEntity.class, pageable);

        GetQuestionResponse response = new GetQuestionResponse();
        response.setQuestions(page.map(questionMapper::map));
        response.setPopularTags(getPopularTags(searchResponse));
        return response;
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

        FilterBuilder filterBuilder = FilterBuilders.matchAllFilter();
        if (!filters.isEmpty()) {
            filterBuilder = FilterBuilders.boolFilter().must(filters.toArray(new FilterBuilder[filters.size()]));
        }

        return filterBuilder;
    }

    private List<Tag> getPopularTags(SearchResponse response) {
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
