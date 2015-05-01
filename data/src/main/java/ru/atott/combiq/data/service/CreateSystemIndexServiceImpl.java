package ru.atott.combiq.data.service;

import org.apache.commons.io.IOUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.atott.combiq.dao.Domains;
import ru.atott.combiq.dao.es.NameVersionDomainResolver;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

@Service
public class CreateSystemIndexServiceImpl implements CreateSystemIndexService {
    @Autowired(required = false)
    private Client client;
    @Autowired
    private NameVersionDomainResolver domainResolver;

    @Override
    public String create(String env) throws IOException, ExecutionException, InterruptedException {
        Long version = domainResolver.getVersionOrDefault(Domains.system, 0L) + 1;
        String indexName = domainResolver.resolveIndexName(Domains.system, version);
        InputStream indexStream = this.getClass().getResourceAsStream("/index/system.json");
        String indexJson = IOUtils.toString(indexStream, "utf-8");
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        request.source(indexJson);
        client.admin().indices().create(request).actionGet();
        return indexName;
    }
}
