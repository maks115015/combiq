package ru.atott.combiq.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.atott.combiq.service.UrlResolver;
import ru.atott.combiq.service.bean.UserQualifier;
import ru.atott.combiq.service.site.Context;
import ru.atott.combiq.service.site.UserContext;
import ru.atott.combiq.web.security.AuthService;
import ru.atott.combiq.web.security.CombiqUser;
import ru.atott.combiq.web.utils.RequestUrlResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;

public class BaseController {

    @Autowired
    private AuthService authService;

    @Autowired
    private HttpServletRequest httpRequest;

    protected int getZeroBasedPage(int page) {
        return Math.max(0, page - 1);
    }

    protected Context getContext() {
        CombiqUser user = authService.getUser();

        Context context = new Context();
        context.setUser(new UserContext());
        context.getUser().setAnonymous(user == null);

        if (user != null) {
            context.getUser().setUserName(user.getName());
            context.getUser().setUserQualifier(new UserQualifier(user.getType(), user.getLogin()));
            context.getUser().setUserId(user.getId());
            context.getUser().setRoles(new HashSet<>(user.getRoles()));
        }

        return context;
    }

    protected UrlResolver getUrlResolver() {
        return new RequestUrlResolver(getHttpRequest());
    }

    protected HttpServletRequest getHttpRequest() {
        return httpRequest;
    }

    protected RedirectView movedPermanently(String url) {
        RedirectView redirectView = new RedirectView(url);
        redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        return redirectView;
    }

    protected ModelAndView notFound() {
        return new ModelAndView("error/404");
    }
}
