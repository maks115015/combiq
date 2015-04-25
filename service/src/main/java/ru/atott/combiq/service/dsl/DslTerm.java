package ru.atott.combiq.service.dsl;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class DslTerm {
    private String value;

    public DslTerm(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("value", value)
                .toString();
    }
}
