package ru.atott.combiq.service.facet;

import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;

import java.util.Optional;

public class UserFacet implements Facet {

    private String userId;

    public UserFacet(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public Optional<FilterBuilder> getFilter(FacetContext context) {
        return Optional.of(FilterBuilders.termFilter("authorId", userId));
    }
}
