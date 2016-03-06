package ru.atott.combiq.service.facet;

import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;

import java.util.Optional;

public class LevelFacet implements Facet {

    private long level;

    public LevelFacet(long level) {
        this.level = level;
    }

    public long getLevel() {
        return level;
    }

    @Override
    public Optional<FilterBuilder> getFilter(FacetContext context) {
        return Optional.of(FilterBuilders.termFilter("level", level));
    }
}
