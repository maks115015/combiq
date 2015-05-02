package ru.atott.combiq.service.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.atott.combiq.dao.entity.QuestionAttrsEntity;
import ru.atott.combiq.dao.repository.QuestionAttrsRepository;
import ru.atott.combiq.service.bean.QuestionAttrs;
import ru.atott.combiq.service.mapper.QuestionAttrsEntityMapper;

@Service
public class QuestionAttrsServiceImpl implements QuestionAttrsService {
    private QuestionAttrsEntityMapper questionAttrsEntityMapper = new QuestionAttrsEntityMapper();
    @Autowired
    private QuestionAttrsRepository questionAttrsRepository;

    @Override
    public QuestionAttrs getQuestionAttrs(String userId, String questionId) {
        QuestionAttrsEntity entity = questionAttrsRepository.findByUserIdAndQuestionId(userId, questionId);
        if (entity == null) {
            return QuestionAttrs.defaultOf(userId, questionId);
        }
        return questionAttrsEntityMapper.map(entity);
    }
}
