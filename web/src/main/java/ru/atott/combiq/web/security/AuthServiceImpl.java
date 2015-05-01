package ru.atott.combiq.web.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public CombiqUser getUser() {
        CombiqUser user = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CombiqUser) {
            user = (CombiqUser) principal;
        }
        return user;
    }
}
