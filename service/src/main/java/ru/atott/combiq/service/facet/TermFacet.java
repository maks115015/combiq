package ru.atott.combiq.service.facet;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.util.Optional;

public class TermFacet implements Facet {

    private String term;

    public TermFacet(String term) {
        this.term = term;
    }

    public String getTerm() {
        return term;
    }

    @Override
    public Optional<QueryBuilder> getQuery(FacetContext context) {
        return Optional.of(QueryBuilders.matchQuery("title", term));
    }
}
