package ru.atott.combiq.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.atott.combiq.web.view.QuestionViewBuilder;

@Controller
public class QuestionController extends BaseController {
    @RequestMapping(value = "/questions/{questionId}")
    public ModelAndView view(@PathVariable("questionId") String questionId) {
        QuestionViewBuilder viewBuilder = new QuestionViewBuilder();
        return viewBuilder.build();
    }
}
