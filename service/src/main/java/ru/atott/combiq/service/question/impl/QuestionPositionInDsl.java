package ru.atott.combiq.service.question.impl;

public class QuestionPositionInDsl {
    private long index;
    private long total;
    private String nextQuestionId;
    private String previosQuestionId;

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String getNextQuestionId() {
        return nextQuestionId;
    }

    public void setNextQuestionId(String nextQuestionId) {
        this.nextQuestionId = nextQuestionId;
    }

    public String getPreviosQuestionId() {
        return previosQuestionId;
    }

    public void setPreviosQuestionId(String previosQuestionId) {
        this.previosQuestionId = previosQuestionId;
    }
}
