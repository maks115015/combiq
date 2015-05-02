package ru.atott.combiq.dao.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import ru.atott.combiq.dao.entity.QuestionAttrsEntity;

@Repository
public interface QuestionAttrsRepository extends ElasticsearchRepository<QuestionAttrsEntity, String> {
    QuestionAttrsEntity findByUserIdAndQuestionId(String userId, String questionId);

}
