package ru.atott.combiq.service.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface Mapper<S, D> {
    D map(S source);

    default List<D> toList(Collection<S> source) {
        return source.stream().map(this::map).collect(Collectors.toList());
    }
}
