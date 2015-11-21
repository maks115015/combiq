package ru.atott.combiq.service.user;

import ru.atott.combiq.service.bean.User;
import ru.atott.combiq.service.bean.UserType;

import java.util.List;

public interface UserService {
    User findByLogin(String login, UserType provider);

    User registerUserViaGithub(GithubRegistrationContext context);

    User registerUserViaVk(VkRegistrationContext context);

    User updateGithubUser(GithubRegistrationContext registrationContext);

    User updateVkUser(VkRegistrationContext registrationContext);

    List<String> getUserRoles(String login);

    User getById(String userId);
}
