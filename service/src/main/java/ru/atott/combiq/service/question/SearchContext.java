package ru.atott.combiq.service.question;

import ru.atott.combiq.service.dsl.DslQuery;

public class SearchContext {
    private int page = 0;
    private int size = 20;
    private DslQuery dslQuery;
    private String userId;

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

    public DslQuery getDslQuery() {
        return dslQuery;
    }

    public void setDslQuery(DslQuery dslQuery) {
        this.dslQuery = dslQuery;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
