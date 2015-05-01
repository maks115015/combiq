package ru.atott.combiq.dao.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;
import ru.atott.combiq.dao.entity.RememberMeEntity;

@Component
public interface RememberMeRepository extends ElasticsearchRepository<RememberMeEntity, String> {
    void deleteByUsername(String username);
}
