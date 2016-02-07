package ru.atott.combiq.web.controller.admin;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ru.atott.combiq.service.bean.User;
import ru.atott.combiq.service.site.SubscriptionType;
import ru.atott.combiq.service.user.UserService;
import ru.atott.combiq.web.bean.SuccessBean;
import ru.atott.combiq.web.controller.BaseController;
import ru.atott.combiq.web.job.SitemapGeneratorJob;
import ru.atott.combiq.web.request.JobSubscribeRequest;
import ru.atott.combiq.web.request.SystemJobPostRequest;

import java.util.Date;
import java.util.List;

@Controller
public class DashboardAdminController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private SitemapGeneratorJob sitemapGeneratorJob;

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('sa','contenter')")
    public ModelAndView dashboard() {
        long countLastRegisteredUsers = userService.getCountRegisteredUsersSince(DateUtils.addDays(new Date(), -14));
        long countAllRegisteredUsers = userService.getCountRegisteredUsers();
        List<User> lastRegisteredUsers = userService.getLastRegisteredUsers(10);

        ModelAndView modelAndView = new ModelAndView("admin/dashboard");
        modelAndView.addObject("countAllRegisteredUsers", countAllRegisteredUsers);
        modelAndView.addObject("countLastRegisteredUsers", countLastRegisteredUsers);
        modelAndView.addObject("lastRegisteredUsers", lastRegisteredUsers);
        return modelAndView;
    }

    @RequestMapping(value = "/admin/job", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAnyRole('sa')")
    public SuccessBean subscribe(@RequestBody SystemJobPostRequest request) {
        try {
            switch (request.getJob()) {
                case "sitemapGeneratorJob":
                    sitemapGeneratorJob.forceGenerateSiteMap();
                    return new SuccessBean();
            }

            return new SuccessBean(false, "Системный процесс " + request.getJob() + " не найден.");
        } catch (Exception e) {
            return new SuccessBean(false, e.getMessage());
        }
    }
}
