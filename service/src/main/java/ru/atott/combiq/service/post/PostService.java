package ru.atott.combiq.service.post;

import org.springframework.data.domain.Page;
import ru.atott.combiq.service.bean.Post;
import ru.atott.combiq.service.site.Context;

public interface PostService {

    Page<Post> getPosts(long page, long size);

    Page<Post> getPublishedPosts(long page, long size);

    Post getPost(String postId);

    String save(Context context, String postId, String title, String content, boolean published);
}
