package ru.atott.combiq.service.user;

import ru.atott.combiq.service.bean.User;
import ru.atott.combiq.service.bean.UserQualifier;
import ru.atott.combiq.service.bean.UserType;

public interface UserService {

    User findByLoginAndType(String login, UserType provider);

    User findByQualifier(UserQualifier userQualifier);

    User findById(String userId);

    User registerUserViaGithub(GithubRegistrationContext context);

    User registerUserViaVk(VkRegistrationContext context);

    User updateGithubUser(GithubRegistrationContext registrationContext);

    User updateVkUser(VkRegistrationContext registrationContext);

    void grantRole(UserQualifier userQualifier, String role) throws UserNotFoundException;

    void revokeRole(UserQualifier userQualifier, String role) throws UserNotFoundException;
}
