package ru.atott.combiq.service.question.impl;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.atott.combiq.service.dsl.DslQuery;

import java.util.Collections;
import java.util.List;

public class SearchContext {
    private int from = 0;
    private int size = 20;
    private DslQuery dslQuery;
    private String userName;
    private String userId;
    private List<String> questionIds;
    private boolean VisibleDeleted;

    public void setQuestionId(String questionId) {
        this.questionIds = Collections.singletonList(questionId);
    }

    public List<String> getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(List<String> questionIds) {
        this.questionIds = questionIds;
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

    public String getUserName() {return userName;}

    public void setUserName(String userName) {this.userName = userName;}

    public boolean isVisibleDeleted() {return VisibleDeleted;}

    public void setVisibleDeleted(boolean visibleDeleted) {VisibleDeleted = visibleDeleted;}

    public String getUserId() {return userId;}

    public void setUserId(String userId) {this.userId = userId;}

    @Override
    public String toString() {
        return "SearchContext{" +
                "from=" + from +
                ", size=" + size +
                ", dslQuery=" + dslQuery +
                ", userName='" + userName + '\'' +
                ", userId='" + userId + '\'' +
                ", questionIds=" + questionIds +
                ", VisibleDeleted=" + VisibleDeleted +
                '}';
    }
}
