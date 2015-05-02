package ru.atott.combiq.service.question;

public interface QuestionReputationService {
    long voteUp(String userId, String questionId);
    long voteDown(String userId, String questionId);
}
