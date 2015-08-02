package ru.atott.combiq.service.question.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.atott.combiq.dao.entity.QuestionAttrsEntity;
import ru.atott.combiq.dao.repository.QuestionAttrsRepository;
import ru.atott.combiq.service.bean.QuestionAttrs;
import ru.atott.combiq.service.mapper.QuestionAttrsMapper;
import ru.atott.combiq.service.question.QuestionAttrsService;

@Service
public class QuestionAttrsServiceImpl implements QuestionAttrsService {
    private QuestionAttrsMapper questionAttrsMapper = new QuestionAttrsMapper();
    @Autowired
    private QuestionAttrsRepository questionAttrsRepository;

    @Override
    public QuestionAttrs getQuestionAttrs(String userId, String questionId) {
        QuestionAttrsEntity entity = questionAttrsRepository.findByUserIdAndQuestionId(userId, questionId);
        if (entity == null) {
            return QuestionAttrs.defaultOf(userId, questionId);
        }
        return questionAttrsMapper.map(entity);
    }
}
