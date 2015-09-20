package ru.atott.combiq.dao.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import ru.atott.combiq.dao.entity.ContentEntity;

@Component
public interface ContentRepository extends CrudRepository<ContentEntity, String> {

}
