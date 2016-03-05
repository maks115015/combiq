package ru.atott.combiq.service.facet;

import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.Optional;

public interface Facet {

    default Optional<QueryBuilder> getQuery(FacetContext context) {
        return Optional.empty();
    }

    default Optional<FilterBuilder> getFilter(FacetContext context) {
        return Optional.empty();
    }
}
