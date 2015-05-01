package ru.atott.combiq.web.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.RedirectView;
import ru.atott.combiq.web.security.AuthService;
import ru.atott.combiq.web.utils.ViewUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CommonViewAttributesInjector extends HandlerInterceptorAdapter {
    private String resourceVersion = String.valueOf(System.currentTimeMillis());
    @Autowired
    private ViewUtils viewUtils;
    @Autowired
    private AuthService authService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null
                && !(modelAndView.getView() instanceof RedirectView)) {

            modelAndView.addObject("utils", viewUtils);
            modelAndView.addObject("env", System.getProperty("env"));
            modelAndView.addObject("resourceVersion", resourceVersion);
            modelAndView.addObject("user", authService.getUser());
        }
    }
}
