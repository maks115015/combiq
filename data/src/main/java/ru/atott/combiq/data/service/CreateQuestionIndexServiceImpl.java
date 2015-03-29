package ru.atott.combiq.data.service;

import com.google.gson.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.atott.combiq.dao.ClientHolder;
import ru.atott.combiq.dao.Domains;
import ru.atott.combiq.dao.Types;
import ru.atott.eshit.client.HitClient;
import ru.atott.eshit.storage.NameVersionDomainResolver;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

@Service
public class CreateQuestionIndexServiceImpl implements CreateQuestionIndexService {
    @Autowired
    private ClientHolder clientHolder;

    @Override
    public String create(String env) throws IOException, ExecutionException, InterruptedException {
        HitClient client = clientHolder.getClient();
        NameVersionDomainResolver domainResolver = (NameVersionDomainResolver) client.getDomainResolver();
        Long version = 1L;
        if (domainResolver.canBeResolved(Domains.question)) {
            version = domainResolver.getVersion(Domains.question) + 1;
        }
        String indexName = Domains.question + domainResolver.getDelimiter() + version;
        InputStream dataStream = this.getClass().getResourceAsStream("/data/questions-1.json");
        String json = IOUtils.toString(dataStream, "utf-8");
        JsonArray questions = new JsonParser().parse(json).getAsJsonArray();
        IOUtils.closeQuietly(dataStream);
        for (JsonElement questionElement: questions) {
            JsonObject questionObject = questionElement.getAsJsonObject();
            String id = questionObject.get("_id").getAsString();
            String source = questionObject.get("_source").toString();
            client.getClient()
                    .prepareIndex(indexName, Types.question, id)
                    .setSource(source)
                    .execute()
                    .get();
        }
        domainResolver.reset();
        return indexName;
    }
}
