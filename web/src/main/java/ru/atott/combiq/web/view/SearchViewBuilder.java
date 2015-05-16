package ru.atott.combiq.web.view;

import org.springframework.web.servlet.ModelAndView;
import ru.atott.combiq.service.bean.Question;
import ru.atott.combiq.service.bean.Tag;
import ru.atott.combiq.web.bean.PagingBean;

import java.util.List;

public class SearchViewBuilder {
    private List<Question> questions;
    private List<Tag> popularTags;
    private PagingBean paging;
    private String dsl;

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

    public String getDsl() {
        return dsl;
    }

    public void setDsl(String dsl) {
        this.dsl = dsl;
    }

    public List<Tag> getPopularTags() {
        return popularTags;
    }

    public void setPopularTags(List<Tag> popularTags) {
        this.popularTags = popularTags;
    }

    public ModelAndView build() {
        ModelAndView mav = new ModelAndView("search");
        mav.addObject("questions", questions);
        mav.addObject("paging", paging);
        mav.addObject("dsl", dsl);
        mav.addObject("popularTags", popularTags);
        return mav;
    }
}
