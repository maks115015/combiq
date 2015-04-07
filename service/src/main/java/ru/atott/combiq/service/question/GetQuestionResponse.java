package ru.atott.combiq.service.question;

import ru.atott.combiq.service.bean.Question;

import java.util.List;

public class GetQuestionResponse {
    private List<Question> questions;

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
