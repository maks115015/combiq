package ru.atott.combiq.service.post;

import org.springframework.data.domain.Page;
import ru.atott.combiq.service.bean.Post;

public interface PostService {

    Page<Post> getPosts(long page, long size);

    Post getPost(String postId);
}
