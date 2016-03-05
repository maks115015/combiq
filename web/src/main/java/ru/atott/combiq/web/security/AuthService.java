package ru.atott.combiq.web.security;

import org.springframework.security.core.Authentication;
import ru.atott.combiq.service.site.UserContext;

public interface AuthService {

    CombiqUser getUser();

    Authentication getAuthentication();

    UserContext getUserContext();

    String getUserId();

    String getLaunchDependentSalt();

    String getSessionId();
}
