package ru.atott.combiq.service.bean;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class UserQualifier {
    public static UserQualifier ATOTT = new UserQualifier(UserType.github, "atott");

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
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserQualifier that = (UserQualifier) o;

        return new EqualsBuilder()
                .append(login, that.login)
                .append(type, that.type)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(login)
                .append(type)
                .toHashCode();
    }

    @Override
    public String toString() {
        return getType().name() + ":" + getLogin();
    }
}
