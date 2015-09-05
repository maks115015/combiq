package ru.atott.combiq.service.user;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class GithubRegistrationContext {
    private String login;
    private String home;
    private String name;
    private String location;
    private String email;
    private String avatarUrl;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("login", login)
                .append("home", home)
                .append("name", name)
                .append("location", location)
                .append("email", email)
                .append("avatarUrl", avatarUrl)
                .toString();
    }
}
