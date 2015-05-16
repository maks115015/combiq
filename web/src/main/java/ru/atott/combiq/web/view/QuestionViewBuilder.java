package ru.atott.combiq.web.view;

import org.springframework.web.servlet.ModelAndView;

public class QuestionViewBuilder {
    public ModelAndView build() {
        ModelAndView mav = new ModelAndView("question");

        return mav;
    }
}
