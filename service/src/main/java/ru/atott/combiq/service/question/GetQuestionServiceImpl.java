package ru.atott.combiq.service.question;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.DefaultResultMapper;
import org.springframework.stereotype.Service;
import ru.atott.combiq.dao.Types;
import ru.atott.combiq.dao.entity.QuestionEntity;
import ru.atott.combiq.dao.es.NameVersionDomainResolver;
import ru.atott.combiq.service.dsl.DslQuery;
import ru.atott.combiq.service.mapper.QuestionEntityToQuestionMapper;

import java.util.ArrayList;
import java.util.List;

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
        List<FilterBuilder> filters = new ArrayList<>();

        QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
        if (!dsl.getTerms().isEmpty()) {
            BoolQueryBuilder termsQueryBuilder = QueryBuilders.boolQuery();
            dsl.getTerms().forEach(term -> {
                termsQueryBuilder.must(QueryBuilders.matchQuery("title", term.getValue()));
            });
            queryBuilder = termsQueryBuilder;
        }

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

        SearchResponse searchResponse = client
                .prepareSearch(domainResolver.resolveQuestionIndex())
                .setTypes(Types.question)
                .setQuery(QueryBuilders.filteredQuery(queryBuilder, filterBuilder))
                .setFrom(context.getPage() * context.getSize())
                .setSize(context.getSize())
                .execute()
                .actionGet();

        Pageable pageable = new PageRequest(context.getPage(), context.getSize());
        Page<QuestionEntity> page = defaultResultMapper.mapResults(searchResponse, QuestionEntity.class, pageable);

        GetQuestionResponse response = new GetQuestionResponse();
        response.setQuestions(page.map(questionMapper::map));
        return response;
    }
}
