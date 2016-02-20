package ru.atott.combiq.web.aop;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.RedirectView;
import ru.atott.combiq.service.UrlResolver;
import ru.atott.combiq.web.security.AuthService;
import ru.atott.combiq.web.security.CombiqUser;
import ru.atott.combiq.web.security.PermissionHelper;
import ru.atott.combiq.web.utils.RequestUrlResolver;
import ru.atott.combiq.web.utils.ViewUtils;
import ru.atott.combiq.web.view.InstantMessageHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CommonViewAttributesInjector extends HandlerInterceptorAdapter {

    private String resourceVersion = String.valueOf(System.currentTimeMillis());

    @Autowired
    private ViewUtils viewUtils;

    @Autowired
    private AuthService authService;

    @Value("${auth.github.clientId}")
    private String githubClientId;

    @Value("${auth.vk.clientId}")
    private String vkClientId;

    @Value("${auth.stackexchange.clientId}")
    private String stackexchangeClientId;

    @Autowired
    private InstantMessageHolder instantMessageHolder;

    @Autowired
    private PermissionHelper permissionHelper;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null
                && !(modelAndView.getView() instanceof RedirectView)) {

            CombiqUser user = authService.getUser();
            UrlResolver urlResolver = new RequestUrlResolver(request);

            modelAndView.addObject("utils", viewUtils);
            modelAndView.addObject("env", System.getProperty("env"));
            modelAndView.addObject("resourceVersion", resourceVersion);
            modelAndView.addObject("user", user);
            modelAndView.addObject("userId", authService.getUserId());
            modelAndView.addObject("userEmail", user != null ? user.getEmail() : null);
            modelAndView.addObject("urlResolver", new RequestUrlResolver(request));

            String sessionId = request.getSession(true).getId();
            String state = DigestUtils.sha256Hex(sessionId + authService.getLaunchDependentSalt());

            modelAndView.addObject("githubClientId", githubClientId);
            modelAndView.addObject("githubClientState", state);
            modelAndView.addObject("vkClientId", vkClientId);
            modelAndView.addObject("vkClientState", state);
            modelAndView.addObject("vkCallbackUrl", urlResolver.externalize("/login/callback/vk.do"));
            modelAndView.addObject("stackexchangeClientId", stackexchangeClientId);
            modelAndView.addObject("stackexchangeClientState", state);
            modelAndView.addObject("stackexchangeCallbackUrl", urlResolver.externalize("/login/callback/stackexchange.do"));
            modelAndView.addObject("instantMessage", instantMessageHolder.get());
            modelAndView.addObject("permissionHelper", permissionHelper);
        }
    }
}
