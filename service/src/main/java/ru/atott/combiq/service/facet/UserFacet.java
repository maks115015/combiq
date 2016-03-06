package ru.atott.combiq.service.facet;

import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;

import java.util.Optional;

public class UserFacet implements Facet {

    private String user;

    public UserFacet(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    @Override
    public Optional<FilterBuilder> getFilter(FacetContext context) {

        if ("me".equals(user)) {
            if (context.getUserContext().isAnonimous()) {
                return Optional.of(FacetUtils.getNothingToFindFilter());
            } else {
                return Optional.of(FilterBuilders.termFilter("authorId", context.getUserContext().getUserId()));
            }
        }

        return Optional.of(FilterBuilders.termFilter("authorId", user));
    }
}
