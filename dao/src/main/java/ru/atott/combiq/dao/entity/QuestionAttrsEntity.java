package ru.atott.combiq.dao.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@Document(indexName = "#{domainResolver.resolveQuestionIndex()}", type = "questionAttrs")
public class QuestionAttrsEntity {
    @Id
    private String id;
    private String userId;
    private String questionId;
    private Long reputation;
    private Date reputationVoteDate;
    private String comment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public Long getReputation() {
        return reputation;
    }

    public void setReputation(Long reputation) {
        this.reputation = reputation;
    }

    public Date getReputationVoteDate() {
        return reputationVoteDate;
    }

    public void setReputationVoteDate(Date reputationVoteDate) {
        this.reputationVoteDate = reputationVoteDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
