package ru.atott.combiq.dao.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;
import ru.atott.combiq.dao.entity.PostEntity;

@Component
public interface PostRepository extends PagingAndSortingRepository<PostEntity, String> {

}
