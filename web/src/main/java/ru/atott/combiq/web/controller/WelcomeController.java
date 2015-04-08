package ru.atott.combiq.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.atott.combiq.service.question.GetQuestionContext;
import ru.atott.combiq.service.question.GetQuestionResponse;
import ru.atott.combiq.service.question.GetQuestionService;
import ru.atott.combiq.web.mapper.QuestionToQestionInfoBeanMapper;

@Controller
public class WelcomeController extends BaseController {
    private QuestionToQestionInfoBeanMapper questionMapper = new QuestionToQestionInfoBeanMapper();

    @Autowired(required = false)
    private GetQuestionService getQuestionService;

    @RequestMapping(value = {"/", "index.do"}, method = RequestMethod.GET)
    public ModelAndView index() {
        GetQuestionContext context = getContext(GetQuestionContext.class);
        context.setSize(400);
        GetQuestionResponse questions = getQuestionService.getQuestions(context);

        ModelAndView mav = new ModelAndView("index");
        mav.addObject("questions", questionMapper.toList(questions.getQuestions()));
        return mav;
    }
}