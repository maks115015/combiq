package ru.atott.combiq.service.question;

import ru.atott.combiq.service.bean.QuestionBean;

import java.util.List;

public interface GetQuestionService {
    List<QuestionBean> getQuestions(GetQuestionContext context);
}
