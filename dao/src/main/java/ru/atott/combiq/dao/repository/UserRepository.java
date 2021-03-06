package ru.atott.combiq.dao.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;
import ru.atott.combiq.dao.entity.UserEntity;

import java.util.Date;
import java.util.List;

@Component
public interface UserRepository extends ElasticsearchRepository<UserEntity, String> {

    List<UserEntity> findByLoginAndType(String login, String type);

    long countByRegisterDateGreaterThanEqual(Date since);
}
