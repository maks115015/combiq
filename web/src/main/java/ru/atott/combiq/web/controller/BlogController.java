package ru.atott.combiq.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.atott.combiq.service.bean.Post;
import ru.atott.combiq.service.post.PostService;
import ru.atott.combiq.web.security.PermissionHelper;
import ru.atott.combiq.web.view.PostViewBuilder;

import java.util.Objects;
import java.util.Optional;

@Controller
public class BlogController extends BaseController {

    @Autowired
    private PostService postService;

    @Autowired
    private PermissionHelper permissionHelper;

    @RequestMapping(value = "/blog", method = RequestMethod.GET)
    public ModelAndView blog(
            @RequestParam(defaultValue = "1") int page) {
        page = getZeroBasedPage(page);

        ModelAndView modelAndView = new ModelAndView("blog");
        modelAndView.addObject("posts", postService.getPublishedPosts(page, 10));
        return modelAndView;
    }

    @RequestMapping(value = {
            "/blog/{postId}",
            "/blog/{postId}/{humanUrlTitle}"
    })
    public Object view(@PathVariable("postId") String postId,
                       @PathVariable("humanUrlTitle") Optional<String> humanUrlTitle) {
        Post post = postService.getPost(postId);

        if (!permissionHelper.isAllowedToView(post)) {
            return notFound();
        }

        if (StringUtils.isNotBlank(post.getHumanUrlTitle())
                && !Objects.equals(post.getHumanUrlTitle(), humanUrlTitle.orElse(null))) {
            return movedPermanently(getUrlResolver().getPostUrl(post));
        }

        PostViewBuilder postViewBuilder = new PostViewBuilder();
        postViewBuilder.setPost(post);
        return postViewBuilder.build();
    }
}
