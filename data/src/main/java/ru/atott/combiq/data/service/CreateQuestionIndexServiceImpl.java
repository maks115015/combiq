package ru.atott.combiq.data.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.atott.combiq.dao.Domains;
import ru.atott.combiq.dao.Types;
import ru.atott.combiq.dao.es.NameVersionDomainResolver;
import ru.atott.combiq.data.utils.DataUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

@Service
public class CreateQuestionIndexServiceImpl implements CreateQuestionIndexService {
    @Autowired(required = false)
    private Client client;
    @Autowired
    private NameVersionDomainResolver domainResolver;

    @Override
    public String create(String env) throws IOException, ExecutionException, InterruptedException {
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
}
