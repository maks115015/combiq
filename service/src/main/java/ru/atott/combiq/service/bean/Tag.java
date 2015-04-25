package ru.atott.combiq.service.bean;

public class Tag {
    private String value;
    private long docCount;

    public Tag(String value, long docCount) {
        this.value = value;
        this.docCount = docCount;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getDocCount() {
        return docCount;
    }

    public void setDocCount(long docCount) {
        this.docCount = docCount;
    }
}
