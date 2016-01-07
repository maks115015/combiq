package ru.atott.combiq.service.question.impl;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.atott.combiq.dao.entity.MarkdownContent;
import ru.atott.combiq.dao.entity.QuestionAttrsEntity;
import ru.atott.combiq.dao.entity.QuestionComment;
import ru.atott.combiq.dao.entity.QuestionEntity;
import ru.atott.combiq.dao.repository.QuestionAttrsRepository;
import ru.atott.combiq.dao.repository.QuestionRepository;
import ru.atott.combiq.service.CombiqConstants;
import ru.atott.combiq.service.question.QuestionService;
import ru.atott.combiq.service.site.Context;
import ru.atott.combiq.service.user.UserRoles;
import ru.atott.combiq.service.user.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionAttrsRepository questionAttrsRepository;

    @Autowired
    private QuestionAttrsEntityBuilder questionAttrsEntityBuilder;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserService userService;

    @Override
    public void saveUserComment(String userId, String questionId, String comment) {
        QuestionAttrsEntity attrsEntity = questionAttrsRepository.findByUserIdAndQuestionId(userId, questionId);
        if (attrsEntity == null) {
            attrsEntity = questionAttrsEntityBuilder.build(questionId, userId);
        }
        attrsEntity.setComment(comment);
        questionAttrsRepository.save(attrsEntity);
    }

    @Override
    public void saveComment(Context context, String questionId, String comment) {
        Validate.isTrue(!context.getUser().isAnonimous());
        Validate.notEmpty(comment);

        QuestionEntity questionEntity = questionRepository.findOne(questionId);
        List<QuestionComment> comments = questionEntity.getComments();

        if (comments == null) {
            comments = new ArrayList<>();
        } else {
            comments = new ArrayList<>(comments);
        }

        QuestionComment questionComment = new QuestionComment();
        questionComment.setContent(new MarkdownContent(null, comment));
        questionComment.setPostDate(new Date());
        questionComment.setUserId(context.getUser().getUserId());
        questionComment.setId(UUID.randomUUID().toString());

        if (context.getUser().getRoles().contains(UserRoles.sa)) {
            questionComment.setUserName(CombiqConstants.combiqUserName);
        } else {
            questionComment.setUserName(context.getUser().getUserName());
        }
        comments.add(questionComment);

        questionEntity.setComments(comments);
        questionRepository.save(questionEntity);
    }

    @Override
    public void saveQuestionBody(String questionId, String body) {
        QuestionEntity questionEntity = questionRepository.findOne(questionId);
        questionEntity.setBody(new MarkdownContent(null, body));
        questionRepository.save(questionEntity);
    }
}
