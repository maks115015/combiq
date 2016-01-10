package ru.atott.combiq.dao.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;
import ru.atott.combiq.dao.entity.Jdk8ClassEntity;

@Component
public interface Jdk8ClassRepository extends PagingAndSortingRepository<Jdk8ClassEntity, String> {

}
