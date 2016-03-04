package ru.atott.combiq.service.facet;

import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;

import java.util.Optional;

public class TagFacet implements Facet {

    private String tag;

    public TagFacet(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public Optional<FilterBuilder> getFilter(FacetContext context) {
        return Optional.of(FilterBuilders.termFilter("tags", tag));
    }
}
