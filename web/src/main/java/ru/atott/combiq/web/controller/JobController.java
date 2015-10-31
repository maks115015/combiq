package ru.atott.combiq.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.atott.combiq.service.site.ContentService;
import ru.atott.combiq.service.site.SubscriptionService;
import ru.atott.combiq.service.site.SubscriptionType;
import ru.atott.combiq.web.bean.SuccessBean;
import ru.atott.combiq.web.request.JobSubscribeRequest;
import ru.atott.combiq.web.security.AuthService;

@Controller
public class JobController {
    @Autowired
    private ContentService contentService;
    @Autowired
    private AuthService authService;
    @Autowired
    private SubscriptionService subscriptionService;

    @RequestMapping(value = "/job")
    public ModelAndView job() {
        ModelAndView modelAndView = new ModelAndView("job/job");
        modelAndView.addObject("jobPageContent", contentService.getContent("job-page"));
        return modelAndView;
    }

    @RequestMapping(value = "/job/opinions")
    public ModelAndView opinions() {
        ModelAndView modelAndView = new ModelAndView("job/opinions");
        modelAndView.addObject("jobOpinionsPageContent", contentService.getContent("job-opinions-page"));
        return modelAndView;
    }

    @RequestMapping(value = "/rest/job/subscription", method = RequestMethod.POST)
    @ResponseBody
    public SuccessBean subscribe(@RequestBody(required = false) JobSubscribeRequest request) {
        if (request == null || StringUtils.isBlank(request.getEmail())) {
            if (authService.getUser() == null
                    || StringUtils.isBlank(authService.getUser().getEmail())) {
                return new SuccessBean(false, "Нам не удалось вас подписать :(.");
            }

            subscriptionService.subscribe(authService.getUser().getEmail(), SubscriptionType.jobPositionsChanges);

            return new SuccessBean(true);
        } else {
            subscriptionService.subscribe(request.getEmail(), SubscriptionType.jobPositionsChanges);
            return new SuccessBean(true);
        }
    }
}
