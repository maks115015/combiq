package ru.atott.combiq.service.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.atott.combiq.dao.entity.QuestionAttrsEntity;
import ru.atott.combiq.dao.entity.QuestionEntity;
import ru.atott.combiq.dao.repository.QuestionAttrsRepository;
import ru.atott.combiq.dao.repository.QuestionRepository;

@Service
public class QuestionReputationServiceImpl implements QuestionReputationService {
    @Autowired
    private QuestionAttrsRepository questionAttrsRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public long voteUp(String userId, String questionId) {
        return vote(userId, questionId, 1);
    }

    @Override
    public long voteDown(String userId, String questionId) {
        return vote(userId, questionId, -1);
    }

    private long vote(String userId, String questionId, long voteReputation) {
        QuestionEntity question = questionRepository.findOne(questionId);

        QuestionAttrsEntity entity = questionAttrsRepository.findByUserIdAndQuestionId(userId, questionId);
        if (entity == null) {
            entity = new QuestionAttrsEntity();
            entity.setQuestionId(questionId);
            entity.setUserId(userId);
        }

        long initialReputation = question.getReputation() == null ? 0L : question.getReputation();
        if (entity.getReputation() != null) {
            initialReputation = initialReputation - entity.getReputation();
        }
        question.setReputation(initialReputation + voteReputation);

        questionAttrsRepository.save(entity);
        questionRepository.save(question);

        return question.getReputation();
    }
}
