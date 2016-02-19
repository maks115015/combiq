package ru.atott.combiq.service.question.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.elasticsearch.action.count.CountRequestBuilder;
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
import ru.atott.combiq.dao.repository.QuestionRepository;
import ru.atott.combiq.service.ServiceException;
import ru.atott.combiq.service.bean.Question;
import ru.atott.combiq.service.bean.QuestionAttrs;
import ru.atott.combiq.service.bean.Tag;
import ru.atott.combiq.service.dsl.DslParser;
import ru.atott.combiq.service.dsl.DslQuery;
import ru.atott.combiq.service.mapper.QuestionAttrsMapper;
import ru.atott.combiq.service.mapper.QuestionMapper;
import ru.atott.combiq.service.question.QuestionService;
import ru.atott.combiq.service.question.SearchQuestionService;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class SearchQuestionServiceImpl implements SearchQuestionService {

    private DefaultResultMapper defaultResultMapper;

    private QuestionAttrsMapper questionAttrsMapper = new QuestionAttrsMapper();

    private LoadingCache<Integer, List<Question>> questionsQithLatestCommentsCache =
            CacheBuilder
                    .newBuilder()
                    .refreshAfterWrite(30, TimeUnit.MINUTES)
                    .build(new CacheLoader<Integer, List<Question>>() {
                        @Override
                        public List<Question> load(Integer key) throws Exception {
                            return getQuestionsWithLatestComments(key);
                        }
                    });

    @Autowired
    private NameVersionDomainResolver domainResolver;

    @Autowired
    private Client client;

    @Autowired
    private QuestionAttrsRepository questionAttrsRepository;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;

    public SearchQuestionServiceImpl() {
        SimpleElasticsearchMappingContext mappingContext = new SimpleElasticsearchMappingContext();
        defaultResultMapper = new DefaultResultMapper(mappingContext);
    }

    @Override
    public long countQuestions(SearchContext context) {
        CountRequestBuilder query = new SearchQuestionElasticQueryBuilder()
                .setClient(client)
                .setDomainResolver(domainResolver)
                .setSearchContext(context)
                .buildCountRequest();

        return query.execute().actionGet().getCount();
    }

    @Override
    public SearchResponse searchQuestions(SearchContext context) {
        SearchRequestBuilder query = new SearchQuestionElasticQueryBuilder()
                .setClient(client)
                .setDomainResolver(domainResolver)
                .setSearchContext(context)
                .buildSearchRequest();

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
    public Optional<SearchResponse> searchAnotherQuestions(Question question) {
        if (CollectionUtils.isEmpty(question.getTags())) {
            return Optional.empty();
        }

        SearchContext searchContext = new SearchContext();
        searchContext.setDslQuery(DslParser.parse("[" + question.getTags().get(0) + "]"));
        searchContext.setSize(5);

        return Optional.of(searchQuestions(searchContext));
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

            SearchResponse searchResponse = searchQuestions(searchContext);
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
            SearchResponse searchResponse = searchQuestions(searchContext);
            if (searchResponse.getQuestions().getContent().size() > 0) {
                response.setQuestion(searchResponse.getQuestions().getContent().get(0));
            }
        }

        if (response.getQuestion() != null) {
            Question question = response.getQuestion();
            if (question.getClassNames() == null) {
                questionService.refreshMentionedClassNames(question);
            }
        }

        return response;
    }

    @Override
    public Question getQuestionByLegacyId(String legacyId) {
        QuestionEntity entity = questionRepository.findOneByLegacyId(legacyId);
        return new QuestionMapper().safeMap(entity);
    }

    @Override
    public List<Question> getQuestionsWithLatestComments(int count) {
        QueryBuilder query = QueryBuilders
                .filteredQuery(
                        QueryBuilders.matchAllQuery(),
                        FilterBuilders.existsFilter("comments.id"));

        SearchRequestBuilder requestBuilder = client
                .prepareSearch(domainResolver.resolveQuestionIndex())
                .setTypes(Types.question)
                .setQuery(query)
                .addSort("comments.postDate", SortOrder.DESC)
                .setSize(count);

        org.elasticsearch.action.search.SearchResponse response = requestBuilder.execute().actionGet();

        List<QuestionEntity> entities = defaultResultMapper
                .mapResults(response, QuestionEntity.class, new PageRequest(0, count))
                .getContent();

        QuestionMapper questionMapper = new QuestionMapper();
        return questionMapper.toList(entities);
    }

    @Override
    public List<Question> get3QuestionsWithLatestComments() {
        try {
            return questionsQithLatestCommentsCache.get(3);
        } catch (ExecutionException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Question> get7QuestionsWithLatestComments() {
        try {
            return questionsQithLatestCommentsCache.get(7);
        } catch (ExecutionException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private List<QuestionAttrs> getQuestionAttrses(Collection<String> questionIds, String userId) {
        if (CollectionUtils.isEmpty(questionIds)) {
            return Collections.emptyList();
        }
        OrFilterBuilder filterBuilder = FilterBuilders.orFilter();
        questionIds.forEach(questionId -> {
            filterBuilder.add(FilterBuilders.andFilter(
                    FilterBuilders.termFilter("questionId", questionId),
                    FilterBuilders.termFilter("userId", userId)));
        });
        QueryBuilder queryBuilder = QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), filterBuilder);
        Iterable<QuestionAttrsEntity> questionAttrsEntities = questionAttrsRepository.search(queryBuilder);
        return questionAttrsMapper.toList(questionAttrsEntities);
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
