package ru.atott.combiq.web.request;

public class EditTagRequest {

    private String tag;

    private String description;

    private String suggestViewOthersQuestionsLabel;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSuggestViewOthersQuestionsLabel() {
        return suggestViewOthersQuestionsLabel;
    }

    public void setSuggestViewOthersQuestionsLabel(String suggestViewOthersQuestionsLabel) {
        this.suggestViewOthersQuestionsLabel = suggestViewOthersQuestionsLabel;
    }
}
