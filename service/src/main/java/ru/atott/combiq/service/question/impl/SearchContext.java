package ru.atott.combiq.service.question.impl;

import ru.atott.combiq.service.dsl.DslQuery;

public class SearchContext {
    private int from = 0;
    private int size = 20;
    private DslQuery dslQuery;
    private String userId;
    private String questionId;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public DslQuery getDslQuery() {
        return dslQuery;
    }

    public void setDslQuery(DslQuery dslQuery) {
        this.dslQuery = dslQuery;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
