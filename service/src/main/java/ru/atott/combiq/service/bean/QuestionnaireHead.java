package ru.atott.combiq.service.bean;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.atott.combiq.dao.entity.MarkdownContent;

import java.util.List;

public class QuestionnaireHead {
    private String name;
    private long questionsCount;
    private String id;
    private MarkdownContent title;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getQuestionsCount() {
        return questionsCount;
    }

    public void setQuestionsCount(long questionsCount) {
        this.questionsCount = questionsCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MarkdownContent getTitle() {
        return title;
    }

    public void setTitle(MarkdownContent title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("questionsCount", questionsCount)
                .append("id", id)
                .append("title", title)
                .toString();
    }
}
