package ru.atott.combiq.web.view;

import org.springframework.web.servlet.ModelAndView;
import ru.atott.combiq.service.bean.Question;
import ru.atott.combiq.web.bean.PagingBean;

import java.util.List;

public class QuestionsViewBuilder {
    private List<Question> questions;
    private PagingBean paging;

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public PagingBean getPaging() {
        return paging;
    }

    public void setPaging(PagingBean paging) {
        this.paging = paging;
    }

    public ModelAndView build() {
        ModelAndView mav = new ModelAndView("questions");
        mav.addObject("questions", questions);
        mav.addObject("paging", paging);
        return mav;
    }
}
