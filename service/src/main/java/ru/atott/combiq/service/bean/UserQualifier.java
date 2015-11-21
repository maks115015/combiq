package ru.atott.combiq.service.bean;

import org.apache.commons.lang.StringUtils;

public class UserQualifier {
    private String login;
    private UserType type;

    public UserQualifier(UserType type, String login) {
        this.type = type;
        this.login = login;
    }

    public UserQualifier(String qualifier) {
        this.type = UserType.valueOf(StringUtils.substringBefore(qualifier, ":"));
        this.login = StringUtils.substringAfter(qualifier, ":");
    }

    public String getLogin() {
        return login;
    }

    public UserType getType() {
        return type;
    }

    @Override
    public String toString() {
        return getType().name() + ":" + getLogin();
    }
}
