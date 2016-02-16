package ru.atott.combiq.service.question.impl;

import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.SetUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.atott.combiq.dao.entity.*;
import ru.atott.combiq.dao.repository.Jdk8ClassRepository;
import ru.atott.combiq.dao.repository.QuestionAttrsRepository;
import ru.atott.combiq.dao.repository.QuestionRepository;
import ru.atott.combiq.service.AccessException;
import ru.atott.combiq.service.CombiqConstants;
import ru.atott.combiq.service.ServiceException;
import ru.atott.combiq.service.bean.Question;
import ru.atott.combiq.service.question.QuestionService;
import ru.atott.combiq.service.site.Context;
import ru.atott.combiq.service.site.EventService;
import ru.atott.combiq.service.site.MarkdownService;
import ru.atott.combiq.service.user.UserRoles;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static ru.atott.combiq.service.user.UserRoles.contenter;
import static ru.atott.combiq.service.user.UserRoles.sa;

@Service
public class QuestionServiceImpl implements QuestionService {

    private static String[] alphabet = 
            {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};

    @Autowired
    private MarkdownService markdownService;

    @Autowired
    private QuestionAttrsRepository questionAttrsRepository;

    @Autowired
    private QuestionAttrsEntityBuilder questionAttrsEntityBuilder;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private Jdk8ClassRepository jdk8ClassRepository;

    @Autowired
    private EventService eventService;

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
        questionComment.setContent(markdownService.toMarkdownContent(comment));
        questionComment.setPostDate(new Date());
        questionComment.setUserId(context.getUser().getUserId());
        questionComment.setId(UUID.randomUUID().toString());

        if (context.getUser().getRoles().contains(sa)) {
            questionComment.setUserName(CombiqConstants.combiqUserName);
        } else {
            questionComment.setUserName(context.getUser().getUserName());
        }
        comments.add(questionComment);

        questionEntity.setComments(comments);
        questionRepository.save(questionEntity);

        eventService.createPostQuestionCommentEvent(context, questionEntity, questionComment.getId());
    }

    @Override
    public void updateComment(Context context, String questionId, String commentId, String commentMarkdown) {
        Validate.isTrue(!context.getUser().isAnonimous());
        Validate.notEmpty(commentMarkdown);

        QuestionEntity questionEntity = questionRepository.findOne(questionId);
        List<QuestionComment> comments = questionEntity.getComments();

        if (comments == null) {
            comments = Collections.emptyList();
        }

        QuestionComment questionComment = comments.stream()
                .filter(comment -> Objects.equals(comment.getId(), commentId))
                .findFirst().orElse(null);

        if (questionComment == null) {
            throw new ServiceException("Question comment " + commentId + " not found.");
        }

        if (!Objects.equals(questionComment.getUserId(), context.getUser().getUserId())) {

            if (Sets.intersection(context.getUser().getRoles(), Sets.newHashSet(sa, contenter)).size() == 0) {
                throw new AccessException();
            }
        }

        questionComment.setContent(markdownService.toMarkdownContent(commentMarkdown));
        questionComment.setEditDate(new Date());

        if (!Objects.equals(questionComment.getUserId(), context.getUser().getUserId())) {
            questionComment.setEditUserId(context.getUser().getUserId());
            questionComment.setEditUserName(context.getUser().getUserName());
        } else {
            questionComment.setEditUserId(null);
            questionComment.setEditUserName(null);
        }

        questionRepository.save(questionEntity);

        eventService.createEditQuestionCommentEvent(context, questionEntity, commentId);
    }

    @Override
    public void saveQuestionBody(String questionId, String body) {
        QuestionEntity questionEntity = questionRepository.findOne(questionId);
        questionEntity.setBody(markdownService.toMarkdownContent(body));
        questionRepository.save(questionEntity);
    }
    @Override
    public void saveQuestion(Question question){
        QuestionEntity questionEntity=new QuestionEntity();
        questionEntity.setTags(question.getTags());
        questionEntity.setLevel(Integer.parseInt(question.getLevel()));
        questionEntity.setTitle(question.getTitle());
        questionEntity.setBody(question.getBody());
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
                .distinct()
                .collect(Collectors.toList());

        List<String> actualClassNames = Collections.emptyList();

        if (classNames.size() != 0) {
            actualClassNames = StreamSupport.stream(jdk8ClassRepository.findAll(classNames).spliterator(), false)
                    .filter(entity -> entity != null)
                    .flatMap(entity -> entity.getClassNames().stream())
                    .distinct()
                    .collect(Collectors.toList());
        }

        question.setClassNames(actualClassNames);

        QuestionEntity questionEntity = questionRepository.findOne(question.getId());
        questionEntity.setClassNames(actualClassNames);
        questionRepository.save(questionEntity);

        return actualClassNames;
    }
}
