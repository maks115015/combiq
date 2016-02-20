package ru.atott.combiq.service.site;

import ru.atott.combiq.service.bean.UserQualifier;

import java.util.Set;

public class UserContext {

    private Set<String> roles;

    private String userId;

    private UserQualifier userQualifier;

    private String userName;

    private boolean anonymous;

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

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }
}
