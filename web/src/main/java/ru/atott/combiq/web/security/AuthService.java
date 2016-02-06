package ru.atott.combiq.web.security;

import org.springframework.security.core.Authentication;

public interface AuthService {

    CombiqUser getUser();

    Authentication getAuthentication();

    String getUserId();

    String getLaunchDependentSalt();

    String getSessionId();
}
