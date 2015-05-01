package ru.atott.combiq.dao.es;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.atott.combiq.dao.DaoException;
import ru.atott.combiq.dao.Domains;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component("domainResolver")
public class NameVersionDomainResolver {
    private String delimiter = "_";
    private Map<String, Long> domainVersions;
    @Autowired(required = false)
    private Client client;

    public String getDelimiter() {
        return delimiter;
    }

    public String resolveIndexName(String domain) {
        return domain + delimiter + getVersion(domain);
    }

    public String resolvePersonalIndex() {
        return resolveIndexName(Domains.personal);
    }

    public String resolveQuestionIndex() {
        return resolveIndexName(Domains.question);
    }

    public boolean canBeResolved(String domain) {
        if (domainVersions == null) {
            updateDomainVersions();
        }
        return domainVersions.containsKey(domain);
    }

    public Long getVersionOrDefault(String domain, Long defaultVersion) {
        if (canBeResolved(domain)) {
            return getVersion(domain);
        }
        return defaultVersion;
    }

    public Long getVersion(String domain) {
        if (domainVersions == null) {
            updateDomainVersions();
        }
        if (!domainVersions.containsKey(domain)) {
            return 1L;
        }
        return domainVersions.get(domain);
    }

    public void reset() {
        updateDomainVersions();
    }

    private void updateDomainVersions() {
        List<String> indexNames = Lists.newArrayList(client
                .admin().cluster().prepareState().execute().actionGet()
                .getState().getMetaData().concreteAllIndices())
                .stream()
                .filter(indexName -> NameVersionUtils.getName(indexName, delimiter) != null)
                .collect(Collectors.toList());

        Set<String> domains = indexNames.stream()
                .map(indexName -> NameVersionUtils.getName(indexName, delimiter))
                .collect(Collectors.toSet());

        domainVersions = domains.stream()
                .map(domain -> {
                    List<Long> versions = indexNames.stream()
                            .filter(indexName -> NameVersionUtils.getName(indexName, delimiter).equals(domain))
                            .map(indexName -> NameVersionUtils.getVersion(indexName, delimiter))
                            .collect(Collectors.toList());
                    versions.sort(Long::compare);
                    return domain + delimiter + versions.get(versions.size() - 1);
                })
                .collect(
                        Collectors.toMap(
                                indexName -> NameVersionUtils.getName(indexName, delimiter),
                                indexName -> NameVersionUtils.getVersion(indexName, delimiter)));
    }
}
