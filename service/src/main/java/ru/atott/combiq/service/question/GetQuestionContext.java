package ru.atott.combiq.service.question;

import ru.atott.combiq.service.Context;

import java.util.List;

public class GetQuestionContext extends Context {
    private int page = 0;
    private int size = 20;
    private List<String> tags;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
