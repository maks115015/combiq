package ru.atott.combiq.service.bean;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class QuestionTag {
    private String tag;
    private String suggestViewOthersQuestionsLabel;
    private String description;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("tag", tag)
                .append("suggestViewOthersQuestionsLabel", suggestViewOthersQuestionsLabel)
                .append("description", description)
                .toString();
    }
}
