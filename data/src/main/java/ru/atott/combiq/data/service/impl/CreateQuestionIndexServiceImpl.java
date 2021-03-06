package ru.atott.combiq.data.service.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.FilteredQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.atott.combiq.dao.Domains;
import ru.atott.combiq.dao.Types;
import ru.atott.combiq.dao.entity.QuestionEntity;
import ru.atott.combiq.dao.entity.QuestionnaireEntity;
import ru.atott.combiq.dao.es.NameVersionDomainResolver;
import ru.atott.combiq.dao.repository.QuestionRepository;
import ru.atott.combiq.dao.repository.QuestionnaireRepository;
import ru.atott.combiq.data.service.CreateQuestionIndexService;
import ru.atott.combiq.data.utils.DataUtils;
import ru.atott.combiq.service.util.NumberService;
import ru.atott.combiq.service.util.TransletirateService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@Service
public class CreateQuestionIndexServiceImpl implements CreateQuestionIndexService {

    @Autowired(required = false)
    private Client client;

    @Autowired
    private NameVersionDomainResolver domainResolver;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Autowired
    private NumberService numberService;

    @Autowired
    private TransletirateService transletirateService;

    @Override
    public String create(String env) throws IOException, ExecutionException, InterruptedException {
        domainResolver.reset();

        Long version = 1L;
        if (domainResolver.canBeResolved(Domains.question)) {
            version = domainResolver.getVersion(Domains.question) + 1;
        }
        String indexName = domainResolver.resolveIndexName(Domains.question, version);

        // Create index.
        InputStream indexStream = this.getClass().getResourceAsStream("/index/question.json");
        String indexJson = IOUtils.toString(indexStream, "utf-8");
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        request.source(indexJson);
        client.admin().indices().create(request).actionGet();

        // Fill data.
        InputStream dataStream = this.getClass().getResourceAsStream("/data/questions-1.json");
        String json = IOUtils.toString(dataStream, "utf-8");
        JsonArray questions = new JsonParser().parse(json).getAsJsonArray();
        IOUtils.closeQuietly(dataStream);
        for (JsonElement questionElement: questions) {
            JsonObject questionObject = questionElement.getAsJsonObject();
            String id = questionObject.get("_id").getAsString();
            String source = questionObject.get("_source").toString();
            client
                    .prepareIndex(indexName, Types.question, id)
                    .setSource(source)
                    .execute()
                    .get();
        }

        domainResolver.reset();
        return indexName;
    }

    @Override
    public String update(String env) throws IOException, ExecutionException, InterruptedException {
        String indexName = domainResolver.resolveQuestionIndex();
        String json = DataUtils.getIndexMapping("/index/question.json");
        DataUtils.putMapping(client, indexName, json);
        return indexName;
    }

    @Override
    public String updateQuestionTimestamps() throws IOException, ExecutionException, InterruptedException {
        String indexName = domainResolver.resolveQuestionIndex();
        FilteredQueryBuilder query = QueryBuilders
                .filteredQuery(
                        QueryBuilders.matchAllQuery(),
                        FilterBuilders.missingFilter("timestamp"));

        SearchRequestBuilder searchRequest = client
                .prepareSearch(indexName)
                .setTypes(Types.question)
                .setQuery(query)
                .addField("id")
                .setSize(Integer.MAX_VALUE);

        SearchResponse searchResponse = searchRequest.execute().get();

        List<String> ids = Arrays.asList(searchResponse.getHits().getHits()).stream()
                .map(hit -> hit.getId())
                .collect(Collectors.toList());

        long timestamp = System.currentTimeMillis();
        for (String id: ids) {
            timestamp++;

            UpdateRequest updateRequest = new UpdateRequest();
            updateRequest.index(indexName);
            updateRequest.type(Types.question);
            updateRequest.id(id);
            updateRequest.doc(
                    jsonBuilder()
                        .startObject()
                            .field("timestamp", timestamp)
                        .endObject());
            client.update(updateRequest).get();
        }

        return indexName;
    }

    @Override
    public void updateHumanUrlTitles() {
        Iterable<QuestionEntity> questionEntities = questionRepository.findAll();
        StreamSupport
                .stream(questionEntities.spliterator(), false)
                .filter(questionEntity -> StringUtils.isBlank(questionEntity.getHumanUrlTitle()))
                .forEach(questionEntity -> {
                    String humanUrlTitle = transletirateService.lowercaseAndTransletirate(questionEntity.getTitle(), 80);
                    questionEntity.setHumanUrlTitle(humanUrlTitle);
                    questionRepository.save(questionEntity);
                });
    }

    @Override
    public void migrateQuestionIdsToNumbers() {
        Map<String, String> questionsIdsMap = new HashMap<>();
        Iterable<QuestionEntity> questionEntities = questionRepository.findAll();
        StreamSupport
                .stream(questionEntities.spliterator(), false)
                .filter(questionEntity -> !NumberUtils.isDigits(questionEntity.getId()))
                .forEach(questionEntity -> {
                    long uniqueNumber = numberService.getUniqueNumber();
                    questionEntity.setLegacyId(questionEntity.getId());
                    questionEntity.setId(Long.toString(uniqueNumber));
                    questionRepository.save(questionEntity);
                    questionRepository.delete(questionEntity.getLegacyId());
                    questionsIdsMap.put(questionEntity.getLegacyId(), questionEntity.getId());
                });

        Iterable<QuestionnaireEntity> questionnaireEntities = questionnaireRepository.findAll();
        StreamSupport
                .stream(questionnaireEntities.spliterator(), false)
                .filter(questionnaireEntity -> questionnaireEntity.getQuestions() != null)
                .forEach(questionnaireEntity -> {
                    questionnaireEntity.setQuestions(
                            questionnaireEntity.getQuestions().stream()
                                    .map(questionId -> questionsIdsMap.getOrDefault(questionId, questionId))
                                    .collect(Collectors.toList()));
                    questionnaireRepository.save(questionnaireEntity);
                });
    }

    @Override
    public void migrateQuestionnaireIdsToNumbers() {
        Iterable<QuestionnaireEntity> entities = questionnaireRepository.findAll();
        StreamSupport
                .stream(entities.spliterator(), false)
                .filter(entity -> !NumberUtils.isDigits(entity.getId()))
                .forEach(entity -> {
                    long uniqueNumber = numberService.getUniqueNumber();
                    entity.setLegacyId(entity.getId());
                    entity.setId(Long.toString(uniqueNumber));
                    questionnaireRepository.save(entity);
                    questionnaireRepository.delete(entity.getLegacyId());
                });
    }
}
