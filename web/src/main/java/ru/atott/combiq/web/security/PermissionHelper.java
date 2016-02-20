package ru.atott.combiq.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.atott.combiq.service.bean.Post;
import ru.atott.combiq.service.user.UserRoles;

import java.util.Arrays;
import java.util.Objects;

@Component
public class PermissionHelper {

    @Autowired
    private AuthService authService;

    public boolean isAllowedToView(Post post) {
        if (post == null) {
            return false;
        }

        if (post.isPublished()) {
            return true;
        }

        CombiqUser user = authService.getUser();

        if (hasAnyRole(UserRoles.sa, UserRoles.contenter)) {
            return true;
        }

        if (Objects.equals(post.getAuthorUserId(), user.getId())) {
            return true;
        }

        return false;
    }

    public boolean isAnonymous() {
        return authService.getUser() == null;
    }

    public boolean hasAnyRole(String... roles) {
        if (roles == null || roles.length == 0) {
            return true;
        }

        if (isAnonymous()) {
            return false;
        }

        CombiqUser combiqUser = authService.getUser();

        return Arrays.asList(roles).stream()
                .anyMatch(role -> combiqUser.getRoles().contains(role));
    }
}
