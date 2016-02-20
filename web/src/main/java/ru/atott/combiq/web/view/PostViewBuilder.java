package ru.atott.combiq.web.view;

import org.springframework.web.servlet.ModelAndView;
import ru.atott.combiq.service.bean.Post;

public class PostViewBuilder {

    private Post post;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public ModelAndView build() {
        ModelAndView modelAndView = new ModelAndView("post");
        modelAndView.addObject("post", post);
        return modelAndView;
    }
}
