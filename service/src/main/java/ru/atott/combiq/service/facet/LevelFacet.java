package ru.atott.combiq.service.facet;

import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;

import java.util.Optional;

public class LevelFacet implements Facet {

    private String level;

    public LevelFacet(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    @Override
    public Optional<FilterBuilder> getFilter(FacetContext context) {
        return Optional.of(FilterBuilders.termFilter("level", level));
    }
}
