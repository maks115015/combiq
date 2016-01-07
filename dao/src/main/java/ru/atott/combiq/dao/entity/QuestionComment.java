package ru.atott.combiq.dao.entity;

import java.util.Date;

public class QuestionComment {

    private String userId;

    private String userName;

    private Date postDate;

    private String id;

    private MarkdownContent content;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public MarkdownContent getContent() {
        return content;
    }

    public void setContent(MarkdownContent content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
