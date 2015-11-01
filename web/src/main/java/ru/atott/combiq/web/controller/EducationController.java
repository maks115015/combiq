package ru.atott.combiq.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EducationController extends BaseController {

    @RequestMapping(value = "/education")
    public ModelAndView view() {
        return new ModelAndView("edu/education");
    }

    @RequestMapping(value = "/education/competence")
    public ModelAndView competence() {
        return new ModelAndView("edu/competence");
    }
}
