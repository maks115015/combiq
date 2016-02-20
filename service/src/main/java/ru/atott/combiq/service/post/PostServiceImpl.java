package ru.atott.combiq.service.post;

import org.apache.commons.lang.Validate;
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
import ru.atott.combiq.service.site.Context;
import ru.atott.combiq.service.site.MarkdownService;
import ru.atott.combiq.service.util.NumberService;
import ru.atott.combiq.service.util.TransletirateService;

import java.util.Date;

@Service
public class PostServiceImpl implements PostService {

    private PostMapper postMapper = new PostMapper();

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MarkdownService markdownService;

    @Autowired
    private NumberService numberService;

    @Autowired
    private TransletirateService transletirateService;

    @Override
    public Page<Post> getPosts(long page, long size) {
        Pageable pageable = new PageRequest((int) page, (int) size, Sort.Direction.DESC, PostEntity.CREATE_DATE_FIELD);
        Page<PostEntity> result = postRepository.findAll(pageable);
        return result.map(postEntity -> postMapper.map(postEntity));
    }

    @Override
    public Page<Post> getPublishedPosts(long page, long size) {
        Pageable pageable = new PageRequest((int) page, (int) size, Sort.Direction.DESC, PostEntity.CREATE_DATE_FIELD);
        Page<PostEntity> result = postRepository.findAllByPublished(true, pageable);
        return result.map(postEntity -> postMapper.map(postEntity));
    }

    @Override
    public Post getPost(String postId) {
        return postMapper.safeMap(postRepository.findOne(postId));
    }

    @Override
    public String save(Context context, String postId, String title, String content, boolean published) {
        Validate.isTrue(!context.getUser().isAnonymous());

        PostEntity postEntity = null;

        if (postId == null) {
            postEntity = new PostEntity();
            postEntity.setAuthorUserId(context.getUser().getUserId());
            postEntity.setAuthorUserName(context.getUser().getUserName());
            postEntity.setCreateDate(new Date());
            postEntity.setId(Long.toString(numberService.getUniqueNumber()));
        } else {
            postEntity = postRepository.findOne(postId);
        }

        if (postEntity == null) {
            throw new PostNotFoundException(postId);
        }

        postEntity.setTitle(title);
        postEntity.setHumanUrlTitle(transletirateService.lowercaseAndTransletirate(title, 80));
        postEntity.setContent(markdownService.toMarkdownContent(content));
        postEntity.setPublished(published);
        postEntity = postRepository.save(postEntity);
        return postEntity.getId();
    }
}
