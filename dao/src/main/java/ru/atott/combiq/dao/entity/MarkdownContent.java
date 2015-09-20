package ru.atott.combiq.dao.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.pegdown.PegDownProcessor;

public class MarkdownContent {
    private String markdown;
    private String html;

    public MarkdownContent() { }

    public MarkdownContent(String markdown) {
        this.markdown = markdown;

        PegDownProcessor processor = new PegDownProcessor();
        this.html = processor.markdownToHtml(markdown);
    }

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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("markdown", markdown)
                .append("html", html)
                .toString();
    }
}
