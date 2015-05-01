package ru.atott.combiq.dao.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;
import ru.atott.combiq.dao.entity.RememberMeEntity;

import java.util.List;

@Component
public interface RememberMeRepository extends ElasticsearchRepository<RememberMeEntity, String> {
    List<RememberMeEntity> findByUsername(String username);
}
