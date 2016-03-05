package ru.atott.combiq.service.facet;

import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;

import java.util.Optional;

public class CommentQuantityFacet implements Facet {

    private long minCount;

    public CommentQuantityFacet(long minCount) {
        this.minCount = minCount;
    }

    public long getMinCount() {
        return minCount;
    }

    @Override
    public Optional<FilterBuilder> getFilter(FacetContext context) {
        if (minCount == 1) {
            return Optional.of(FilterBuilders.existsFilter("comments.id"));
        } else {
            return Optional.of(
                    FilterBuilders
                        .andFilter(
                                FilterBuilders.existsFilter("comments.id"),
                                FilterBuilders
                                        .scriptFilter("_source.comments && _source.comments.size >= quantity")
                                        .addParam("quantity", minCount)
                        ));
        }
    }
}
