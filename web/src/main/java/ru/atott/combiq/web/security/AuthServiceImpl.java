package ru.atott.combiq.web.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private static String launchDependentSalt = String.valueOf(System.currentTimeMillis());

    @Override
    public CombiqUser getUser() {
        CombiqUser user = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CombiqUser) {
            user = (CombiqUser) principal;
        }
        return user;
    }

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public String getUserId() {
        CombiqUser user = getUser();
        if (user == null) {
            return null;
        }
        return user.getId();
    }

    @Override
    public String getLaunchDependentSalt() {
        return launchDependentSalt;
    }
}
