package ru.atott.combiq.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.atott.combiq.service.site.ContentService;
import ru.atott.combiq.web.bean.SuccessBean;
import ru.atott.combiq.web.request.ContentRequest;

@Controller
public class ContentController extends BaseController {
    @Autowired
    private ContentService contentService;

    @RequestMapping(value = "/content/{contentId}", method = RequestMethod.POST)
    @ResponseBody
    @Secured("sa")
    public Object preview(@PathVariable("contentId") String contentId,
                          @RequestBody ContentRequest contentRequest) {
        contentService.updateContent(contentId, contentRequest.getContent());
        return new SuccessBean();
    }
}
