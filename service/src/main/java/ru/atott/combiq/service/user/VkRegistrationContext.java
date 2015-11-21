package ru.atott.combiq.service.user;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class VkRegistrationContext {
    private String uid;
    private String name;
    private String location;
    private String avatarUrl;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("uid", uid)
                .append("name", name)
                .append("location", location)
                .append("avatarUrl", avatarUrl)
                .toString();
    }
}
