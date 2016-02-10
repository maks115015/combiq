package ru.atott.combiq.dao.entity;

public class Link {

    private String url;

    private String text;

    public Link() { }

    public Link(String text, String url) {
        this.url = url;
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
