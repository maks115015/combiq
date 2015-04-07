package ru.atott.combiq.service.question;

import ru.atott.combiq.service.bean.Question;

import java.util.List;

public interface GetQuestionService {
    GetQuestionResponse getQuestions(GetQuestionContext context);
}
