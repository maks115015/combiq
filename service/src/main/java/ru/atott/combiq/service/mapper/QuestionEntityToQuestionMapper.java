package ru.atott.combiq.service.mapper;

import ru.atott.combiq.dao.entity.QuestionEntity;
import ru.atott.combiq.service.bean.Question;

public class QuestionEntityToQuestionMapper implements Mapper<QuestionEntity, Question> {
    @Override
    public Question map(QuestionEntity source) {
        Question question = new Question();
        question.setId(source.getId());
        question.setTitle(source.getTitle());
        return question;
    }
}
