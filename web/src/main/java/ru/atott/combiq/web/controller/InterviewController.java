package ru.atott.combiq.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.atott.combiq.service.question.QuestionnaireService;
import ru.atott.combiq.service.site.ContentService;
import ru.atott.combiq.service.site.UserContext;

@Controller
public class InterviewController extends BaseController {

    @Autowired
    private QuestionnaireService questionnaireService;

    @Autowired
    private ContentService contentService;

    @RequestMapping(value = "/interview")
    public ModelAndView interview() {
        UserContext uc = getUc();
        ModelAndView modelAndView = new ModelAndView("interview/interview");
        modelAndView.addObject("questionnaires", questionnaireService.getQuestionnaires());
        modelAndView.addObject("questionnairesPageContent", contentService.getContent(uc, "questionnaires-page"));
        modelAndView.addObject("questionnairesPageBottomContent", contentService.getContent(uc, "questionnaires-page-bottom"));
        return modelAndView;
    }

    @RequestMapping(value = "/interview/arrange")
    public ModelAndView interviewArrange() {
        UserContext uc = getUc();
        ModelAndView modelAndView = new ModelAndView("interview/arrange");
        modelAndView.addObject("interviewPageContent", contentService.getContent(uc, "interview-page"));
        return modelAndView;
    }

    @RequestMapping(value = "/interview/education")
    public ModelAndView interviewEducation() {
        UserContext uc = getUc();
        ModelAndView modelAndView = new ModelAndView("/interview/education");
        modelAndView.addObject("educationPageContent", contentService.getContent(uc, "education-page"));
        return modelAndView;
    }

    @RequestMapping(value = "/interview/competence")
    public ModelAndView interviewCompetence() {
        return new ModelAndView("/interview/competence");
    }

    // Old links.

    @RequestMapping(value = "/questionnaires/interview")
    public RedirectView legacyInterview() {
        return movedPermanently("/interview/arrange");
    }

    @RequestMapping(value = { "/questionnaires", "/questionnaires/prepare" })
    public RedirectView legacyList() {
        return movedPermanently("/interview");
    }

    @RequestMapping(value = "/education")
    public RedirectView view() {
        return movedPermanently("/interview/education");
    }

    @RequestMapping(value = "/education/competence")
    public RedirectView competence() {
        return movedPermanently("/interview/competence");
    }
}
