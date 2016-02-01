package ru.atott.combiq.web.controller.admin;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.atott.combiq.service.bean.User;
import ru.atott.combiq.service.user.UserService;
import ru.atott.combiq.web.bean.PagingBean;
import ru.atott.combiq.web.bean.PagingBeanBuilder;
import ru.atott.combiq.web.controller.BaseController;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserAdminController extends BaseController {

    private PagingBeanBuilder pagingBeanBuilder = new PagingBeanBuilder();

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('sa')")
    public ModelAndView list(
            HttpServletRequest httpServletRequest,
            @RequestParam(defaultValue = "1") int page) {
        page = getZeroBasedPage(page);

        Page<User> registeredUsers = userService.getRegisteredUsers(page, 1);

        PagingBean paging = pagingBeanBuilder.build(registeredUsers, page, httpServletRequest);

        ModelAndView modelAndView = new ModelAndView("admin/users");
        modelAndView.addObject("paging", paging);
        modelAndView.addObject("registeredUsers", Lists.newArrayList(registeredUsers.getContent()));
        return modelAndView;
    }
}
