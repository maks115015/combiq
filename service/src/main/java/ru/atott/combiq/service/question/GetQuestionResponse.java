package ru.atott.combiq.service.question;

import org.springframework.data.domain.Page;
import ru.atott.combiq.service.bean.Question;

import java.util.List;

public class GetQuestionResponse {
    private Page<Question> questions;

    public Page<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Page<Question> questions) {
        this.questions = questions;
    }
}
