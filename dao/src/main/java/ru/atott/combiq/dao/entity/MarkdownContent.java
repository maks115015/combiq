package ru.atott.combiq.dao.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.pegdown.PegDownProcessor;

public class MarkdownContent {

    private String markdown;

    private String html;

    private String id;

    public MarkdownContent() { }

    public String getMarkdown() {
        return markdown;
    }

    public void setMarkdown(String markdown) {
        this.markdown = markdown;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("markdown", markdown)
                .append("html", html)
                .append("id", id)
                .toString();
    }
}
