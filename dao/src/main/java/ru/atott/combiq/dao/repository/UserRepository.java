package ru.atott.combiq.dao.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;
import ru.atott.combiq.dao.entity.UserEntity;

import java.util.List;

@Component
public interface UserRepository extends ElasticsearchRepository<UserEntity, String> {
    List<UserEntity> findByEmail(String email);
    
    List<UserEntity> findByLogin(String login);
}
