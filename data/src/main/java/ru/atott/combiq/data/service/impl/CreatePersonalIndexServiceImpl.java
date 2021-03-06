package ru.atott.combiq.data.service.impl;

import org.apache.commons.io.IOUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.atott.combiq.dao.Domains;
import ru.atott.combiq.dao.es.NameVersionDomainResolver;
import ru.atott.combiq.data.service.CreatePersonalIndexService;
import ru.atott.combiq.data.utils.DataUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

@Service
public class CreatePersonalIndexServiceImpl implements CreatePersonalIndexService {
    @Autowired(required = false)
    private Client client;
    @Autowired
    private NameVersionDomainResolver domainResolver;

    @Override
    public String create(String env) throws IOException, ExecutionException, InterruptedException {
        domainResolver.reset();

        Long version = domainResolver.getVersionOrDefault(Domains.personal, 0L) + 1;
        String indexName = domainResolver.resolveIndexName(Domains.personal, version);
        InputStream indexStream = this.getClass().getResourceAsStream("/index/personal.json");
        String indexJson = IOUtils.toString(indexStream, "utf-8");
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        request.source(indexJson);
        client.admin().indices().create(request).actionGet();

        domainResolver.reset();
        return indexName;
    }

    @Override
    public String update(String env) throws IOException, ExecutionException, InterruptedException {
        String indexName = domainResolver.resolvePersonalIndex();
        String json = DataUtils.getIndexMapping("/index/personal.json");
        DataUtils.putMapping(client, indexName, json);
        return indexName;
    }
}
