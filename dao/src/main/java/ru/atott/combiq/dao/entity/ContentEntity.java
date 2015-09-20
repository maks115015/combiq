package ru.atott.combiq.dao.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "#{domainResolver.resolveSiteIndex()}", type = "content")
public class ContentEntity {
    @Id
    private String id;
    private MarkdownContent content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MarkdownContent getContent() {
        return content;
    }

    public void setContent(MarkdownContent content) {
        this.content = content;
    }
}
