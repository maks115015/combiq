package ru.atott.combiq.service.question.impl;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.atott.combiq.dao.entity.MarkdownContent;
import ru.atott.combiq.dao.entity.QuestionAttrsEntity;
import ru.atott.combiq.dao.entity.QuestionComment;
import ru.atott.combiq.dao.entity.QuestionEntity;
import ru.atott.combiq.dao.repository.Jdk8ClassRepository;
import ru.atott.combiq.dao.repository.QuestionAttrsRepository;
import ru.atott.combiq.dao.repository.QuestionRepository;
import ru.atott.combiq.service.CombiqConstants;
import ru.atott.combiq.service.bean.Question;
import ru.atott.combiq.service.question.QuestionService;
import ru.atott.combiq.service.site.Context;
import ru.atott.combiq.service.user.UserRoles;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class QuestionServiceImpl implements QuestionService {

    private static String[] alphabet = 
            {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};

    @Autowired
    private QuestionAttrsRepository questionAttrsRepository;

    @Autowired
    private QuestionAttrsEntityBuilder questionAttrsEntityBuilder;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private Jdk8ClassRepository jdk8ClassRepository;

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

    @Override
    public List<String> refreshMentionedClassNames(Question question) {
        String text = question.getTitle();

        List<String> classNames = Arrays.asList(StringUtils.split(text, " ")).stream()
                .filter(StringUtils::isNotBlank)
                .filter(s -> StringUtils.startsWithAny(s.toLowerCase(), alphabet))
                .map(s -> s.replaceAll("[^A-z.]", ""))
                .map(s -> StringUtils.removeEnd(s, "."))
                .collect(Collectors.toList());

        List<String> actualClassNames = StreamSupport.stream(jdk8ClassRepository.findAll(classNames).spliterator(), false)
                .filter(entity -> entity != null)
                .flatMap(entity -> entity.getClassNames().stream())
                .collect(Collectors.toList());

        question.setClassNames(actualClassNames);

        QuestionEntity questionEntity = questionRepository.findOne(question.getId());
        questionEntity.setClassNames(actualClassNames);
        questionRepository.save(questionEntity);

        return actualClassNames;
    }
}
