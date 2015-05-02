package ru.atott.combiq.service.bean;

public class QuestionAttrs {
    private String questionId;
    private String userId;
    private Long reputation;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getReputation() {
        return reputation;
    }

    public void setReputation(Long reputation) {
        this.reputation = reputation;
    }

    public static QuestionAttrs defaultOf(String userId, String questionId) {
        QuestionAttrs attrs = new QuestionAttrs();
        attrs.setQuestionId(questionId);
        attrs.setUserId(userId);
        return attrs;
    }
}
