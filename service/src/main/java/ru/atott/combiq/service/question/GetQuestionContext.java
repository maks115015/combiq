package ru.atott.combiq.service.question;

import ru.atott.combiq.service.Context;

public class GetQuestionContext extends Context {
    private long page = 0;
    private long size = 20;

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
