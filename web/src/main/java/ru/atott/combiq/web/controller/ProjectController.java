package ru.atott.combiq.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProjectController extends BaseController {

    @RequestMapping(value = "/project")
    public ModelAndView project() {
        ModelAndView modelAndView = new ModelAndView("project");
        return modelAndView;
    }
}
