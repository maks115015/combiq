package ru.atott.combiq.service.user;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class FacebookRegistrationContext {
    private String id;
    private String name;
    private String avatarUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                .append("id", id)
                .append("name", name)
                .append("avatarUrl", avatarUrl)
                .toString();
    }
}
