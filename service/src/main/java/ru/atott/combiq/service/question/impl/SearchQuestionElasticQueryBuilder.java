package ru.atott.combiq.service.question.impl;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.elasticsearch.action.count.CountRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.global.GlobalBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.sort.SortOrder;
import ru.atott.combiq.dao.Types;
import ru.atott.combiq.dao.entity.QuestionEntity;
import ru.atott.combiq.dao.es.NameVersionDomainResolver;
import ru.atott.combiq.service.dsl.DslQuery;

import java.util.ArrayList;
import java.util.List;

public class SearchQuestionElasticQueryBuilder {

    private SearchContext searchContext;

    private NameVersionDomainResolver domainResolver;

    private Client client;

    public SearchQuestionElasticQueryBuilder() { }

    public SearchQuestionElasticQueryBuilder setSearchContext(SearchContext searchContext) {
        this.searchContext = searchContext;
        return this;
    }

    public SearchQuestionElasticQueryBuilder setDomainResolver(NameVersionDomainResolver domainResolver) {
        this.domainResolver = domainResolver;
        return this;
    }

    public SearchQuestionElasticQueryBuilder setClient(Client client) {
        this.client = client;
        return this;
    }

    public SearchRequestBuilder buildSearchRequest() {
        Validate.notNull(searchContext);
        Validate.notNull(domainResolver);
        Validate.notNull(client);

        SearchRequestBuilder result = client
                .prepareSearch(domainResolver.resolveQuestionIndex())
                .setTypes(Types.question)
                .setQuery(QueryBuilders.filteredQuery(getQueryBuilder(), getFilterBuilder()))
                .addSort(QuestionEntity.TIMESTAMP_FIELD, SortOrder.DESC)
                .setFrom(searchContext.getFrom())
                .setSize(searchContext.getSize());

        getAggregationBuilders().stream().forEach(result::addAggregation);

        return result;
    }

    public CountRequestBuilder buildCountRequest() {
        Validate.notNull(searchContext);
        Validate.notNull(domainResolver);
        Validate.notNull(client);

        CountRequestBuilder result = client
                .prepareCount(domainResolver.resolveQuestionIndex())
                .setTypes(Types.question)
                .setQuery(QueryBuilders.filteredQuery(getQueryBuilder(), getFilterBuilder()));

        return result;
    }

    private QueryBuilder getQueryBuilder() {
        DslQuery dsl = searchContext.getDslQuery();

        QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();

        List<QueryBuilder> conditions = new ArrayList<>();

        if (dsl != null && !dsl.getTerms().isEmpty()) {
            BoolQueryBuilder termsQueryBuilder = QueryBuilders.boolQuery();
            dsl.getTerms().forEach(term -> {
                termsQueryBuilder.must(QueryBuilders.matchQuery("title", term.getValue()));
            });
            conditions.add(termsQueryBuilder);
        }
        if (!conditions.isEmpty()) {
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            conditions.forEach(boolQueryBuilder::must);
            queryBuilder = boolQueryBuilder;
        }

        return queryBuilder;
    }

    private FilterBuilder getFilterBuilder() {
        DslQuery dsl = searchContext.getDslQuery();
        List<String> questionIds = searchContext.getQuestionIds();

        List<FilterBuilder> filters = new ArrayList<>();

        if (dsl!=null && dsl.getUserId()!=null){
            BoolFilterBuilder userFilter = FilterBuilders.boolFilter();
            userFilter.mustNot(FilterBuilders.termFilter("authorId", searchContext.getDslQuery().getUserId()));
            filters.add(userFilter);
            if(!dsl.isVisibleDeleted()){
                BoolFilterBuilder deleteFilter = FilterBuilders.boolFilter();
                deleteFilter.mustNot(FilterBuilders.termFilter("deleted", true));
                filters.add(deleteFilter);
            }
        } else if(dsl!=null && !dsl.isVisibleDeleted()){
            BoolFilterBuilder deleteFilter = FilterBuilders.boolFilter();
            deleteFilter.mustNot(FilterBuilders.termFilter("deleted", true));
            filters.add(deleteFilter);
        }

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

        if (dsl != null && dsl.getMinCommentQuantity() != null) {
            if (dsl.getMinCommentQuantity().equals(1L)) {
                filters.add(FilterBuilders.existsFilter("comments.id"));
            } else {
                filters.add(FilterBuilders.andFilter(
                        FilterBuilders
                                .existsFilter("comments.id"),
                        FilterBuilders
                                .scriptFilter("_source.comments && _source.comments.size >= quantity")
                                .addParam("quantity", dsl.getMinCommentQuantity())));
            }
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

    private List<AggregationBuilder> getAggregationBuilders() {
        GlobalBuilder global = AggregationBuilders.global("global");

        TermsBuilder popularTags = AggregationBuilders
                .terms("popularTags")
                .field("tags")
                .size(10);

        global.subAggregation(popularTags);

        return Lists.newArrayList(global);
    }
}
