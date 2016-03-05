package ru.atott.combiq.service.site;

import ru.atott.combiq.service.bean.UserQualifier;

import java.util.Arrays;
import java.util.Set;

public class UserContext {

    private Set<String> roles;

    private String userId;

    private UserQualifier userQualifier;

    private String userName;

    private boolean anonimous;

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UserQualifier getUserQualifier() {
        return userQualifier;
    }

    public void setUserQualifier(UserQualifier userQualifier) {
        this.userQualifier = userQualifier;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isAnonimous() {
        return anonimous;
    }

    public void setAnonimous(boolean anonimous) {
        this.anonimous = anonimous;
    }

    public boolean hasAnyRole(String... roles) {
        return Arrays.asList(roles).stream().anyMatch(role -> this.roles.contains(role));
    }
}
