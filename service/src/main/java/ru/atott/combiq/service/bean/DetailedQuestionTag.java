package ru.atott.combiq.service.bean;

public class DetailedQuestionTag extends Tag {
    private QuestionTag details;

    public DetailedQuestionTag(String value, long docCount) {
        super(value, docCount);
    }

    public QuestionTag getDetails() {
        return details;
    }

    public void setDetails(QuestionTag details) {
        this.details = details;
    }
}
