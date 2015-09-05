package ru.atott.combiq.web.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.atott.combiq.service.bean.User;
import ru.atott.combiq.service.bean.UserType;
import ru.atott.combiq.service.user.UserService;

import java.nio.charset.Charset;

public class CombiqUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        try {
            User byEmail = userService.findByLogin(login);
            if (byEmail != null) {
                String passwordHash = byEmail.getPasswordHash();
                switch (byEmail.getType()) {
                    case github:
                        passwordHash = DigestUtils.sha1Hex("github");
                        break;
                }

                CombiqUser combiqUser = new CombiqUser(login, passwordHash);
                combiqUser.setType(byEmail.getType());
                combiqUser.setLogin(byEmail.getLogin());
                combiqUser.setId(byEmail.getId());
                combiqUser.setAvatarUrl(byEmail.getAvatarUrl());
                return combiqUser;
            }
        } catch (Exception e) {
            throw new UsernameNotFoundException(e.getMessage(), e);
        }
        throw new UsernameNotFoundException(String.format("User '%s' is not found.", login));
    }
}
