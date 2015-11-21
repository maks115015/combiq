package ru.atott.combiq.web.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
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
import ru.atott.combiq.service.UrlResolver;
import ru.atott.combiq.service.bean.User;
import ru.atott.combiq.service.bean.UserType;
import ru.atott.combiq.service.user.GithubRegistrationContext;
import ru.atott.combiq.service.user.UserService;
import ru.atott.combiq.service.user.VkRegistrationContext;
import ru.atott.combiq.web.security.AuthService;
import ru.atott.combiq.web.utils.RequestUrlResolver;
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
    @Value("${auth.vk.clientId}")
    private String vkClientId;
    @Value("${auth.vk.clientSecret}")
    private String vkClientSecret;
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
        modelAndView.addObject("vkClientId", vkClientId);
        modelAndView.addObject("vkClientSecret", vkClientSecret);
        return modelAndView;
    }

    @RequestMapping(value = "/login/callback/vk.do", method = RequestMethod.GET)
    public RedirectView vkCallback(@RequestParam(value = "code") String code,
                                   HttpServletRequest httpRequest,
                                   HttpServletResponse httpResponse) throws IOException, ServletException {
        UrlResolver urlResolver = new RequestUrlResolver(httpRequest);

        String exchangeUrl = UriComponentsBuilder
                .fromHttpUrl("https://oauth.vk.com/access_token")
                .queryParam("client_id", vkClientId)
                .queryParam("client_secret", vkClientSecret)
                .queryParam("code", code)
                .queryParam("redirect_uri", urlResolver.externalize("/login/callback/vk.do"))
                .build()
                .toString();

        HttpClient client = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(exchangeUrl);
        httpPost.setHeader("Accept", "application/json");
        HttpResponse response = client.execute(httpPost);
        String responseJson = IOUtils.toString(response.getEntity().getContent());
        JsonObject responseJsonObject = ViewUtils.parseJson(responseJson);
        String accessToken = responseJsonObject.get("access_token").getAsString();

        String getUrl = UriComponentsBuilder
                .fromHttpUrl("https://api.vk.com/method/users.get")
                .queryParam("access_token", accessToken)
                .queryParam("fields", "photo_50,city,site")
                .build()
                .toUriString();
        HttpGet httpGet = new HttpGet(getUrl);
        httpGet.setHeader("Accept", "application/json");
        response = client.execute(httpGet);
        responseJson = IOUtils.toString(response.getEntity().getContent(), "utf-8");
        responseJsonObject = ViewUtils.parseJson(responseJson);

        JsonObject userInfo = responseJsonObject.getAsJsonArray("response").get(0).getAsJsonObject();
        String uid = userInfo.get("uid").getAsString();
        String firstName = userInfo.get("first_name").getAsString();
        String lastName = userInfo.get("last_name").getAsString();
        String city = getDefaultString(userInfo.get("city"));
        String photo = getDefaultString(userInfo.get("photo_50"));
        String site = getDefaultString(userInfo.get("site"));

        if (StringUtils.isNoneBlank(site) && StringUtils.startsWithIgnoreCase(site, "http://")) {
            site = "http://" + site;
        }

        User user = userService.findByLogin(uid, UserType.vk);

        VkRegistrationContext vkRegistrationContext = new VkRegistrationContext();
        vkRegistrationContext.setUid(uid);
        vkRegistrationContext.setName(firstName + " " + lastName);
        vkRegistrationContext.setLocation(city);
        vkRegistrationContext.setAvatarUrl(photo);

        if (user == null) {
            user = userService.registerUserViaVk(vkRegistrationContext);
        } else {
            user = userService.updateVkUser(vkRegistrationContext);
        }

        httpRequest.login(user.getQualifier().toString(), "vk");
        rememberMeServices.loginSuccess(httpRequest, httpResponse, authService.getAuthentication());

        return new RedirectView("/");
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

        String userLogin = responseJsonObject.get("login").getAsString().toLowerCase();
        User user = userService.findByLogin(userLogin, UserType.github);

        GithubRegistrationContext registrationContext = new GithubRegistrationContext();
        registrationContext.setLogin(userLogin);
        registrationContext.setHome(getDefaultString(responseJsonObject.get("html_url")));
        registrationContext.setName(getDefaultString(responseJsonObject.get("name")));
        if (StringUtils.isBlank(registrationContext.getName())) {
            registrationContext.setName(userLogin);
        }
        registrationContext.setLocation(getDefaultString(responseJsonObject.get("location")));
        registrationContext.setAvatarUrl(getDefaultString(responseJsonObject.get("avatar_url")));
        registrationContext.setEmail(getDefaultString(responseJsonObject.get("email")));

        if (user == null) {
            user = userService.registerUserViaGithub(registrationContext);
        } else {
            user = userService.updateGithubUser(registrationContext);
        }

        httpRequest.login(user.getQualifier().toString(), "github");
        rememberMeServices.loginSuccess(httpRequest, httpResponse, authService.getAuthentication());

        return new RedirectView("/");
    }

    private String getDefaultString(JsonElement value) {
        if (value == null || value.isJsonNull()) {
            return null;
        }
        return StringUtils.defaultIfBlank(value.getAsString(), null);
    }
}
