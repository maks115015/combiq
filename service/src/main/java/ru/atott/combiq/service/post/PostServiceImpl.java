package ru.atott.combiq.service.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.atott.combiq.dao.entity.PostEntity;
import ru.atott.combiq.dao.repository.PostRepository;
import ru.atott.combiq.service.bean.Post;
import ru.atott.combiq.service.mapper.PostMapper;

@Service
public class PostServiceImpl implements PostService {

    private PostMapper postMapper = new PostMapper();

    @Autowired
    private PostRepository postRepository;

    @Override
    public Page<Post> getPosts(long page, long size) {
        Pageable pageable = new PageRequest((int) page, (int) size, Sort.Direction.DESC, PostEntity.CREATE_DATE_FIELD);
        Page<PostEntity> result = postRepository.findAll(pageable);
        return result.map(postEntity -> postMapper.map(postEntity));
    }

    @Override
    public Post getPost(String postId) {
        return postMapper.safeMap(postRepository.findOne(postId));
    }
}
