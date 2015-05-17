package ru.atott.combiq.service.question.impl;

import org.springframework.stereotype.Component;
import ru.atott.combiq.dao.entity.QuestionAttrsEntity;

@Component
public class QuestionAttrsEntityBuilder {
    public QuestionAttrsEntity build(String questionId, String userId) {
        QuestionAttrsEntity entity = new QuestionAttrsEntity();
        entity.setUserId(userId);
        entity.setQuestionId(questionId);
        return entity;
    }
}
