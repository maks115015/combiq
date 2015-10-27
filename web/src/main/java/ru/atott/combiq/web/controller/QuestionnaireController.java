package ru.atott.combiq.web.controller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.atott.combiq.service.bean.Questionnaire;
import ru.atott.combiq.service.question.QuestionnaireService;
import ru.atott.combiq.service.site.ContentService;
import ru.atott.combiq.web.bean.SuccessBean;
import ru.atott.combiq.web.request.ContentRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Controller
public class QuestionnaireController extends BaseController {
    @Autowired
    private QuestionnaireService questionnaireService;
    @Autowired
    private ContentService contentService;

    @RequestMapping(value = "/questionnaires")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView("questionnaires/questionnaires");
        modelAndView.addObject("questionnaires", questionnaireService.getQuestionnaires());
        modelAndView.addObject("questionnairesPageContent", contentService.getContent("questionnaires-page"));
        return modelAndView;
    }

    @RequestMapping(value = "/interview")
    public ModelAndView interview() {
        ModelAndView modelAndView = new ModelAndView("questionnaires/interview");
        modelAndView.addObject("interviewPageContent", contentService.getContent("interview-page"));
        return modelAndView;
    }

    @RequestMapping(value = "/questionnaire/{questionnaireId}")
    public ModelAndView view(@PathVariable("questionnaireId") String questionnaireId) {
        Questionnaire questionnaire = questionnaireService.getQuestionnaire(questionnaireId);

        ModelAndView modelAndView = new ModelAndView("questionnaire");
        modelAndView.addObject("questionnaire", questionnaire);
        return modelAndView;
    }

    @RequestMapping(value = "/questionnaire/{questionnaireId}/{title}.pdf", method = RequestMethod.GET)
    public void view(@PathVariable("questionnaireId") String questionnaireId,
                     @PathVariable("title") String title,
                     HttpServletResponse response) throws Exception {
        Questionnaire questionnaire = questionnaireService.getQuestionnaire(questionnaireId);

        response.setContentType("application/pdf");

        questionnaireService.exportQuestionnareToPdf(questionnaire, response.getOutputStream());
        response.flushBuffer();
    }

    @RequestMapping(value = "/questionnaire/{questionnaireId}/title", method = RequestMethod.POST)
    @ResponseBody
    @Secured("sa")
    public Object updateTitle(@PathVariable("questionnaireId") String questionnaireId,
                              @RequestBody ContentRequest title) {
        questionnaireService.updateQuestionnaireTitle(questionnaireId, title.getContent());
        return new SuccessBean();
    }
}
