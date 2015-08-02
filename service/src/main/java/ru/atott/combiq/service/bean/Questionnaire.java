package ru.atott.combiq.service.bean;

import java.util.List;

public class Questionnaire extends QuestionnaireHead {
    private List<Question> questions;

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
