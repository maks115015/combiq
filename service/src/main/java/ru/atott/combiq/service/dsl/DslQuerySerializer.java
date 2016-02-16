package ru.atott.combiq.service.dsl;

import org.apache.commons.lang3.StringUtils;

public class DslQuerySerializer {
    public DslQuerySerializer() { }

    public String serialize(DslQuery query) {
        StringBuilder builder = new StringBuilder();

        if (StringUtils.isNoneBlank(query.getLevel())) {
            builder.append("level:").append(query.getLevel());
        }

        query.getTerms().forEach(term -> {
            builder.append(" ");
            builder.append(term.getValue());
        });

        query.getTags().forEach(tag -> {
            builder.append(" ");
            builder.append("[").append(tag.getValue()).append("]");
        });

        if (query.getMinCommentQuantity() != null) {
            builder.append(" ");
            builder.append("comments:").append(query.getMinCommentQuantity());
        }

        return builder.toString().trim();
    }
}
