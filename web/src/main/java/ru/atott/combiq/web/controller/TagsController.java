package ru.atott.combiq.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ru.atott.combiq.service.bean.DetailedQuestionTag;
import ru.atott.combiq.service.bean.QuestionTag;
import ru.atott.combiq.service.question.TagService;

import java.util.List;

@Controller
public class TagsController extends BaseController {
    @Autowired
    private TagService tagService;

    @RequestMapping(value = "/tags")
    public ModelAndView tags() {
        ModelAndView modelAndView = new ModelAndView("tags");
        modelAndView.addObject("tags", tagService.getAllQuestionTags());
        return modelAndView;
    }
    @RequestMapping(value = "questions/tags")
    @ResponseBody
    public String[] getAvaibleTags(){
        return tagService.getAllTags();
    }
}
