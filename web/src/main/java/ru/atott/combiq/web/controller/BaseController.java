package ru.atott.combiq.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.view.RedirectView;
import ru.atott.combiq.service.site.UserContext;
import ru.atott.combiq.web.security.AuthService;

public class BaseController {

    @Autowired
    private AuthService authService;

    protected int getZeroBasedPage(int page) {
        return Math.max(0, page - 1);
    }

    protected UserContext getUc() {
        return authService.getUserContext();
    }

    protected RedirectView movedPermanently(String url) {
        RedirectView redirectView = new RedirectView(url);
        redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        return redirectView;
    }
}
