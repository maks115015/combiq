package ru.atott.combiq.service.facet;

import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;

import java.util.Optional;

import static ru.atott.combiq.service.user.UserRoles.contenter;
import static ru.atott.combiq.service.user.UserRoles.sa;

public class DeletedFacet implements Facet {

    private boolean deleted;

    public DeletedFacet(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public Optional<FilterBuilder> getFilter(FacetContext context) {
        if (deleted) {
            if (!context.getUserContext().isAnonimous()) {
                // Только неанонимные пользователи могут просматривать удаленные вопросы.

                if (context.getUserContext().hasAnyRole(sa, contenter)) {
                    // Администраторы могут просматривать удаленные вопросы всех пользователей.

                    return Optional.of(FilterBuilders.termFilter("deleted", true));
                } else {
                    // Пользователи могут просматривать только свои удаленные вопросы.

                    return Optional.of(
                            FilterBuilders.andFilter(
                                    FilterBuilders.termFilter("deleted", true),
                                    FilterBuilders.termFilter("authorId", context.getUserContext().getUserId())));
                }
            } else {
                return Optional.of(FacetUtils.getNothingToFindFilter());
            }
        } else {
            return Optional.of(
                    FilterBuilders.orFilter(
                            FilterBuilders.termFilter("deleted", false),
                            FilterBuilders.missingFilter("deleted")));
        }
    }
}
