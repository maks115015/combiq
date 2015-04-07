package ru.atott.combiq.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.atott.combiq.dao.entity.UserEntity;
import ru.atott.combiq.dao.repository.UserRepository;
import ru.atott.combiq.service.question.GetQuestionContext;
import ru.atott.combiq.service.question.GetQuestionResponse;
import ru.atott.combiq.service.question.GetQuestionService;

@Controller
public class WelcomeController extends BaseController {
    @Autowired(required = false)
    private GetQuestionService getQuestionService;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = {"/", "index.do"}, method = RequestMethod.GET)
    public ModelAndView index() {
        UserEntity one = userRepository.findOne("1");

        GetQuestionContext context = getContext(GetQuestionContext.class);
        context.setSize(400);
        GetQuestionResponse questions = getQuestionService.getQuestions(context);

        ModelAndView mav = new ModelAndView("index");
        mav.addObject("questions", questions.getQuestions());
        return mav;
    }
}