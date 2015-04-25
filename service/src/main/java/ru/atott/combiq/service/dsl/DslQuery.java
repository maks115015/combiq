package ru.atott.combiq.service.dsl;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collections;
import java.util.List;

public class DslQuery {
    private List<DslTag> tags = Collections.emptyList();
    private List<DslTerm> terms = Collections.emptyList();
    private String level;

    public List<DslTag> getTags() {
        return tags;
    }

    public void setTags(List<DslTag> tags) {
        this.tags = tags;
    }

    public List<DslTerm> getTerms() {
        return terms;
    }

    public void setTerms(List<DslTerm> terms) {
        this.terms = terms;
    }

    public String toDsl() {
        DslQuerySerializer serializer = new DslQuerySerializer();
        return serializer.serialize(this);
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("tags", tags)
                .append("terms", terms)
                .append("level", level)
                .toString();
    }
}
