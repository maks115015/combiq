package ru.atott.combiq.dao.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Document(indexName = "#{domainResolver.resolveQuestionIndex()}", type = "questionnaire")
public class QuestionnaireEntity {
    @Id
    private String id;
    private String name;
    private List<String> questions;
    private MarkdownContent title;
    private MarkdownContent description;

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

    public List<String> getQuestions() {
        return questions;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

    public MarkdownContent getTitle() {
        return title;
    }

    public void setTitle(MarkdownContent title) {
        this.title = title;
    }

    public MarkdownContent getDescription() {
        return description;
    }

    public void setDescription(MarkdownContent description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("questions", questions)
                .append("title", title)
                .append("description", description)
                .toString();
    }
}
