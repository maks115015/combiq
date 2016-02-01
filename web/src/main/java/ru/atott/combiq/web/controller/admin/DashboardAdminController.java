package ru.atott.combiq.web.controller.admin;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.atott.combiq.service.bean.User;
import ru.atott.combiq.service.user.UserService;
import ru.atott.combiq.web.controller.BaseController;

import java.util.Date;
import java.util.List;

@Controller
public class DashboardAdminController extends BaseController {

    @Autowired
    private UserService userService;

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
}
