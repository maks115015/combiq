package ru.atott.combiq.dao.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "#{domainResolver.resolveQuestionIndex()}", type = "tag")
public class TagEntity {
    @Id
    private String name;
    private String suggestViewOthersQuestionsLabel;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuggestViewOthersQuestionsLabel() {
        return suggestViewOthersQuestionsLabel;
    }

    public void setSuggestViewOthersQuestionsLabel(String suggestViewOthersQuestionsLabel) {
        this.suggestViewOthersQuestionsLabel = suggestViewOthersQuestionsLabel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
