package ru.atott.combiq.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.atott.combiq.service.question.GetQuestionContext;
import ru.atott.combiq.service.question.GetQuestionService;
import ru.atott.combiq.service.bean.QuestionBean;

import java.util.List;

@Controller
public class WelcomeController extends BaseController {
    @Autowired
    private GetQuestionService getQuestionService;

    @RequestMapping(value = {"/", "index.do"}, method = RequestMethod.GET)
    public ModelAndView index() {
        GetQuestionContext context = getContext(GetQuestionContext.class);
        context.setSize(400);
        List<QuestionBean> questions = getQuestionService.getQuestions(context);

        ModelAndView mav = new ModelAndView("index");
        mav.addObject("questions", questions);
        return mav;
    }
}