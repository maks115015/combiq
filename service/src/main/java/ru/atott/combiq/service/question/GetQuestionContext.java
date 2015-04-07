package ru.atott.combiq.service.question;

import ru.atott.combiq.service.Context;

public class GetQuestionContext extends Context {
    private int page = 0;
    private int size = 20;

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
