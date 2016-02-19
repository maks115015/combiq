package ru.atott.combiq.web.bean;

public class CountQuestionSearchBean {

    private long count;

    public CountQuestionSearchBean() { }

    public CountQuestionSearchBean(long count) {
        this.count = count;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
