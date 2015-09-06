package ru.atott.combiq.dao.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import ru.atott.combiq.dao.entity.TagEntity;

@Component
public interface TagRepository extends CrudRepository<TagEntity, String> {

}
