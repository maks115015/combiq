package ru.atott.combiq.service.bean;

import ru.atott.combiq.dao.entity.MarkdownContent;

import java.util.List;

public class Questionnaire extends QuestionnaireHead {
    private List<Question> questions;
    private MarkdownContent description;

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public MarkdownContent getDescription() {
        return description;
    }

    public void setDescription(MarkdownContent description) {
        this.description = description;
    }
}
