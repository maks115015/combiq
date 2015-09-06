package ru.atott.combiq.web.view;

import org.springframework.web.servlet.ModelAndView;
import ru.atott.combiq.service.bean.Question;
import ru.atott.combiq.service.bean.QuestionTag;
import ru.atott.combiq.service.question.impl.QuestionPositionInDsl;

import java.util.List;

public class QuestionViewBuilder {
    private Question question;
    private QuestionPositionInDsl positionInDsl;
    private String dsl;
    private List<QuestionTag> tags;

    public String getDsl() {
        return dsl;
    }

    public void setDsl(String dsl) {
        this.dsl = dsl;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public QuestionPositionInDsl getPositionInDsl() {
        return positionInDsl;
    }

    public void setPositionInDsl(QuestionPositionInDsl positionInDsl) {
        this.positionInDsl = positionInDsl;
    }

    public List<QuestionTag> getTags() {
        return tags;
    }

    public void setTags(List<QuestionTag> tags) {
        this.tags = tags;
    }

    public ModelAndView build() {
        ModelAndView mav = new ModelAndView("question");
        mav.addObject("question", question);
        mav.addObject("position", positionInDsl);
        mav.addObject("dsl", dsl);
        mav.addObject("tags", tags);
        return mav;
    }
}
