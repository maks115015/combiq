package ru.atott.combiq.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.atott.combiq.service.bean.Questionnaire;
import ru.atott.combiq.service.question.QuestionnaireService;

@Controller
public class QuestionnaireController extends BaseController {
    @Autowired
    private QuestionnaireService questionnaireService;

    @RequestMapping(value = "/questionnaires")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView("questionnaires");
        modelAndView.addObject("questionnaires", questionnaireService.getQuestionnaires());
        return modelAndView;
    }

    @RequestMapping(value = "/questionnaire/{questionnaireId}")
    public ModelAndView view(@PathVariable("questionnaireId") String questionnaireId) {
        Questionnaire questionnaire = questionnaireService.getQuestionnaire(questionnaireId);

        ModelAndView modelAndView = new ModelAndView("questionnaire");
        modelAndView.addObject("questionnaire", questionnaire);
        return modelAndView;
    }
}
