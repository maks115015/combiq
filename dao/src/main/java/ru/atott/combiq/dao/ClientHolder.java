package ru.atott.combiq.dao;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.atott.eshit.client.HitClient;
import ru.atott.eshit.storage.NameVersionDomainResolver;

@Service
public class ClientHolder {
    private volatile HitClient client = null;

    @Value("${es.host}")
    private String host;
    @Value("${es.port}")
    private int port;
    @Value("${es.cluster}")
    private String cluster;

    public HitClient getClient() {
        if (client == null) {
            synchronized(ClientHolder.class) {
                if (client == null) {
                    Settings settings = ImmutableSettings.settingsBuilder()
                            .put("cluster.name", cluster)
                            .build();
                    Client transportClient = new TransportClient(settings)
                            .addTransportAddress(new InetSocketTransportAddress(host, port));

                    NameVersionDomainResolver domainResolver = new NameVersionDomainResolver("_", transportClient);
                    client = new HitClient(transportClient, domainResolver);
                }
            }
        }
        return client;
    }
}
