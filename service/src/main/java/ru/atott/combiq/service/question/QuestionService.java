package ru.atott.combiq.service.question;

import ru.atott.combiq.service.site.Context;

public interface QuestionService {

    void saveUserComment(String userId, String questionId, String comment);

    void saveComment(Context context, String questionId, String comment);

    void saveQuestionBody(String questionId, String body);
}
