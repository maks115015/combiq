package ru.atott.combiq.service.question;

import ru.atott.combiq.service.bean.QuestionAttrs;

public interface QuestionAttrsService {
    QuestionAttrs getQuestionAttrs(String userId, String questionId);
}
