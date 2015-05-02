package ru.atott.combiq.service.mapper;

import ru.atott.combiq.dao.entity.QuestionEntity;
import ru.atott.combiq.service.bean.Question;
import ru.atott.combiq.service.bean.QuestionAttrs;

import java.util.Collections;
import java.util.Map;

public class QuestionEntityMapper implements Mapper<QuestionEntity, Question> {
    private Map<String, QuestionAttrs> attrsMap;
    private String userId;

    public QuestionEntityMapper() { }

    public QuestionEntityMapper(String userId, Map<String, QuestionAttrs> attrsMap) {
        this.userId = userId;
        this.attrsMap = attrsMap;
    }

    @Override
    public Question map(QuestionEntity source) {
        String questionId = source.getId();

        Question question = new Question();
        question.setId(questionId);
        question.setTitle(source.getTitle());
        question.setTags(source.getTags());
        if (question.getTags() == null) {
            question.setTags(Collections.emptyList());
        }
        question.setLevel("D" + source.getLevel());
        if (source.getReputation() == null) {
            question.setReputation(0);
        } else {
            question.setReputation(source.getReputation());
        }
        if (userId != null) {
            question.setAttrs(attrsMap.getOrDefault(questionId, QuestionAttrs.defaultOf(userId, questionId)));
        }
        return question;
    }
}
