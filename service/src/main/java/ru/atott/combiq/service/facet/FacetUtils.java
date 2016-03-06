package ru.atott.combiq.service.facet;

import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;

public final class FacetUtils {

    private FacetUtils() { }

    public static FilterBuilder getNothingToFindFilter() {
        return FilterBuilders.termFilter("nothingToFind", true);
    }
}
