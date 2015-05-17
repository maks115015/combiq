package ru.atott.combiq.service.question;

public interface QuestionService {
    void saveComment(String userId, String questionId, String comment);
}
