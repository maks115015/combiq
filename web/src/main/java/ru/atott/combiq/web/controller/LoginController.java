package ru.atott.combiq.web.controller;

import com.google.gson.JsonObject;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;
import ru.atott.combiq.service.bean.User;
import ru.atott.combiq.service.user.GithubRegistrationContext;
import ru.atott.combiq.service.user.UserService;
import ru.atott.combiq.web.security.AuthService;
import ru.atott.combiq.web.utils.ViewUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LoginController extends BaseController {
    @Value("${auth.github.clientId}")
    private String githubClientId;
    @Value("${auth.github.clientSecret}")
    private String githubClientSecret;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;
    @Autowired
    private RememberMeServices rememberMeServices;

    @RequestMapping(value = "/login.do", method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("githubClientId", githubClientId);
        modelAndView.addObject("githubClientSecret", githubClientSecret);
        return modelAndView;
    }

    @RequestMapping(value = "/login/callback/github.do", method = RequestMethod.GET)
    public RedirectView githubCallback(@RequestParam(value = "code") String code,
                                       HttpServletRequest httpRequest,
                                       HttpServletResponse httpResponse) throws IOException, ServletException {
        String exchangeUrl = UriComponentsBuilder
                .fromHttpUrl("https://github.com/login/oauth/access_token")
                .queryParam("client_id", githubClientId)
                .queryParam("client_secret", githubClientSecret)
                .queryParam("code", code)
                .build()
                .toUriString();

        HttpClient client = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(exchangeUrl);
        httpPost.setHeader("Accept", "application/json");
        HttpResponse response = client.execute(httpPost);
        String responseJson = IOUtils.toString(response.getEntity().getContent());
        JsonObject responseJsonObject = ViewUtils.parseJson(responseJson);
        String accessToken = responseJsonObject.get("access_token").getAsString();

        String getUrl = UriComponentsBuilder
                .fromHttpUrl("https://api.github.com/user")
                .queryParam("access_token", accessToken)
                .build()
                .toUriString();
        HttpGet httpGet = new HttpGet(getUrl);
        httpGet.setHeader("Accept", "application/json");
        response = client.execute(httpGet);
        responseJson = IOUtils.toString(response.getEntity().getContent());
        responseJsonObject = ViewUtils.parseJson(responseJson);

        String userEmail = responseJsonObject.get("email").getAsString().toLowerCase();
        User user = userService.findByEmail(userEmail);

        GithubRegistrationContext registrationContext = new GithubRegistrationContext();
        registrationContext.setLogin(responseJsonObject.get("login").getAsString());
        registrationContext.setHome(responseJsonObject.get("html_url").getAsString());
        registrationContext.setName(responseJsonObject.get("name").getAsString());
        registrationContext.setLocation(responseJsonObject.get("location").getAsString());
        registrationContext.setAvatarUrl(responseJsonObject.get("avatar_url").getAsString());
        registrationContext.setEmail(userEmail);

        if (user == null) {
            user = userService.registerUserViaGithub(registrationContext);
        } else {
            user = userService.updateGithubUser(registrationContext);
        }

        httpRequest.login(user.getEmail(), "github");
        rememberMeServices.loginSuccess(httpRequest, httpResponse, authService.getAuthentication());

        return new RedirectView("/");
    }
}
