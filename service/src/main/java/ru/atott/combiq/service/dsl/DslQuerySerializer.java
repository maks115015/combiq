package ru.atott.combiq.service.dsl;

public class DslQuerySerializer {
    public DslQuerySerializer() { }

    public String serialize(DslQuery query) {
        StringBuilder builder = new StringBuilder();

        query.getTerms().forEach(term -> {
            builder.append(" ");
            builder.append(term.getValue());
        });

        query.getTags().forEach(tag -> {
            builder.append(" ");
            builder.append("[").append(tag.getValue()).append("]");
        });

        return builder.toString().trim();
    }
}
