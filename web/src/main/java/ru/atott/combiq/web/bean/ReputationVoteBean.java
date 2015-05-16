package ru.atott.combiq.web.bean;

public class ReputationVoteBean {
    private String questionId;
    private long userReputation;
    private long questionReputation;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public long getUserReputation() {
        return userReputation;
    }

    public void setUserReputation(long userReputation) {
        this.userReputation = userReputation;
    }

    public long getQuestionReputation() {
        return questionReputation;
    }

    public void setQuestionReputation(long questionReputation) {
        this.questionReputation = questionReputation;
    }
}
