package ru.atott.combiq.web.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.atott.combiq.service.bean.User;
import ru.atott.combiq.service.user.UserService;

public class CombiqUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        try {
            User user = userService.findByLogin(login);
            if (user != null) {
                String passwordHash = user.getPasswordHash();
                switch (user.getType()) {
                    case github:
                        passwordHash = DigestUtils.sha1Hex("github");
                        break;
                }

                CombiqUser combiqUser = new CombiqUser(login, passwordHash);
                combiqUser.setType(user.getType());
                combiqUser.setLogin(user.getLogin());
                combiqUser.setId(user.getId());
                combiqUser.setAvatarUrl(user.getAvatarUrl());
                return combiqUser;
            }
        } catch (Exception e) {
            throw new UsernameNotFoundException(e.getMessage(), e);
        }
        throw new UsernameNotFoundException(String.format("User '%s' is not found.", login));
    }
}
