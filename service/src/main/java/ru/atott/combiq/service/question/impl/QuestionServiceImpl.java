package ru.atott.combiq.service.question.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.atott.combiq.dao.entity.QuestionAttrsEntity;
import ru.atott.combiq.dao.repository.QuestionAttrsRepository;
import ru.atott.combiq.service.question.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionAttrsRepository questionAttrsRepository;
    @Autowired
    private QuestionAttrsEntityBuilder questionAttrsEntityBuilder;

    @Override
    public void saveComment(String userId, String questionId, String comment) {
        QuestionAttrsEntity attrsEntity = questionAttrsRepository.findByUserIdAndQuestionId(userId, questionId);
        if (attrsEntity == null) {
            attrsEntity = questionAttrsEntityBuilder.build(questionId, userId);
        }
        attrsEntity.setComment(comment);
        questionAttrsRepository.save(attrsEntity);
    }
}
