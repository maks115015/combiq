package ru.atott.combiq.service.bean;

public class QuestionAttrs {
    private String questionId;
    private String userId;
    private Long reputation;
    private String comment;

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public static QuestionAttrs defaultOf(String userId, String questionId) {
        QuestionAttrs attrs = new QuestionAttrs();
        attrs.setQuestionId(questionId);
        attrs.setUserId(userId);
        return attrs;
    }
}
