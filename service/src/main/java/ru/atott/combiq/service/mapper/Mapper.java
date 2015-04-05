package ru.atott.combiq.service.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface Mapper<A, B> {
    B map(A a);

    default List<B> toList(Collection<A> collection) {
        return collection.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
