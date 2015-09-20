package ru.atott.combiq.service.user;

import ru.atott.combiq.service.bean.User;

import java.util.List;

public interface UserService {
    User findByEmail(String email);

    User findByLogin(String login);

    User registerUserViaGithub(GithubRegistrationContext context);

    User updateGithubUser(GithubRegistrationContext registrationContext);

    List<String> getUserRoles(String login);
}
