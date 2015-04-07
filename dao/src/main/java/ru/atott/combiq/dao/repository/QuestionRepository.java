package ru.atott.combiq.dao.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;
import ru.atott.combiq.dao.entity.QuestionEntity;

@Component
public interface QuestionRepository extends PagingAndSortingRepository<QuestionEntity, String> {

}
