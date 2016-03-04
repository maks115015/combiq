package ru.atott.combiq.web.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.atott.combiq.service.UrlResolver;
import ru.atott.combiq.service.bean.Question;
import ru.atott.combiq.service.dsl.DslParser;
import ru.atott.combiq.service.question.QuestionService;
import ru.atott.combiq.service.search.SearchService;
import ru.atott.combiq.service.question.TagService;
import ru.atott.combiq.service.question.impl.GetQuestionContext;
import ru.atott.combiq.service.question.impl.GetQuestionResponse;
import ru.atott.combiq.web.bean.SuccessBean;
import ru.atott.combiq.web.request.ContentRequest;
import ru.atott.combiq.web.request.EditCommentRequest;
import ru.atott.combiq.web.request.QuestionRequest;
import ru.atott.combiq.web.security.AuthService;
import ru.atott.combiq.web.utils.RequestUrlResolver;
import ru.atott.combiq.web.view.QuestionViewBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class QuestionController extends BaseController {

    @Autowired
    private AuthService authService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private TagService tagService;

    @RequestMapping(value = {
            "/questions/{questionId}",
            "/questions/{questionId}/{humanUrlTitle}"
    })
    public Object view(@PathVariable("questionId") String questionId,
                       @PathVariable("humanUrlTitle") Optional<String> humanUrlTitle,
                       @RequestParam(value = "index", required = false) Integer index,
                       @RequestParam(value = "dsl", required = false) String dsl,
                       HttpServletRequest request) {
        UrlResolver urlResolver = new RequestUrlResolver(request);

        GetQuestionContext context = new GetQuestionContext();
        context.setUserId(authService.getUserId());
        context.setId(questionId);

        if (index != null) {
            context.setProposedIndexInDslResponse(index);
            context.setDsl(DslParser.parse(dsl));
        }

        GetQuestionResponse questionResponse = searchService.getQuestion(context);

        RedirectView redirectView = redirectToCanonicalUrlIfNeed(questionId, humanUrlTitle.orElse(null), questionResponse, request);

        if (redirectView != null) {
            return redirectView;
        }

        List<Question> anotherQuestions = null;
        if (questionResponse.getQuestion().isLanding()) {
            anotherQuestions = searchService
                    .searchAnotherQuestions(questionResponse.getQuestion())
                    .map(response -> response.getQuestions().getContent())
                    .orElse(null);
        }

        List<Question> questionsWithLatestComments = Collections.emptyList();
        if (CollectionUtils.isEmpty(questionResponse.getQuestion().getComments())) {
            questionsWithLatestComments = searchService.get3QuestionsWithLatestComments();
        }
        QuestionViewBuilder viewBuilder = new QuestionViewBuilder();
        viewBuilder.setQuestion(questionResponse.getQuestion());
        viewBuilder.setPositionInDsl(questionResponse.getPositionInDsl());
        viewBuilder.setDsl(dsl);
        viewBuilder.setTags(tagService.getTags(questionResponse.getQuestion().getTags()));
        viewBuilder.setCanonicalUrl(urlResolver.externalize(urlResolver.getQuestionUrl(questionResponse.getQuestion())));
        viewBuilder.setAnotherQuestions(anotherQuestions);
        viewBuilder.setQuestionsWithLatestComments(questionsWithLatestComments);
        viewBuilder.setQuestionsFeed(searchService.get7QuestionsWithLatestComments());
        return viewBuilder.build();
    }

    @RequestMapping(value = "/questions/{questionId}/content", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAnyRole('sa','contenter')")
    public Object postContent(@PathVariable("questionId") String questionId,
                              @RequestBody ContentRequest contentRequest) {
        questionService.saveQuestionBody(questionId, contentRequest.getContent());
        return new SuccessBean();
    }

    @RequestMapping(value = "/questions/{questionId}/comment", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAnyRole('user')")
    public Object postComment(@PathVariable("questionId") String questionId,
                              @RequestBody EditCommentRequest request) {
        if (request.getCommentId() == null) {
            questionService.saveComment(getContext(), questionId, request.getContent());
        } else {
            questionService.updateComment(getContext(), questionId, request.getCommentId(), request.getContent());
        }
        return new SuccessBean();
    }

    private RedirectView redirectToCanonicalUrlIfNeed(String questionId,
                                                      String humanUrlTitle,
                                                      GetQuestionResponse searchResponse,
                                                      HttpServletRequest request) {
        Question question = searchResponse.getQuestion();

        if (question == null) {
            question = searchService.getQuestionByLegacyId(questionId);

            if (question == null) {
                return null;
            }

            UrlResolver urlResolver = new RequestUrlResolver(request);
            String questionUrl = urlResolver.getQuestionUrl(question, request.getQueryString());
            return movedPermanently(questionUrl);
        }

        if (StringUtils.isNotBlank(question.getHumanUrlTitle())
                && !Objects.equals(question.getHumanUrlTitle(), humanUrlTitle)) {
            UrlResolver urlResolver = new RequestUrlResolver(request);
            String questionUrl = urlResolver.getQuestionUrl(question, request.getQueryString());
            return movedPermanently(questionUrl);
        }

        return null;
    }
    @RequestMapping(value = "/questions/new",method = RequestMethod.POST)
    @ResponseBody
    public SuccessBean saveQuestion(@RequestBody QuestionRequest questionRequest){
        if(questionRequest.getId()==null && questionRequest.getTitle()!=null && !questionRequest.getTags().isEmpty()){
            questionService.saveQuestion(getContext(),questionRequestToQuestion(questionRequest));
            return new SuccessBean(true);
        }
        return new SuccessBean(false);
    }

    @RequestMapping(value = "/questions/{questionId}", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAnyRole('sa','contenter')")
    public SuccessBean updateQuestion(@PathVariable("questionId") String questionId,@RequestBody QuestionRequest questionRequest) {
        if (questionId.equals(questionRequest.getId())) {
            questionService.saveQuestion(getContext(),questionRequestToQuestion(questionRequest));
            return new SuccessBean(true);
        }
        return new SuccessBean(false);
    }
    //Вынес  в отдельный метод
    private Question questionRequestToQuestion(QuestionRequest questionRequest){// Вот мне не очень нравиться что в
        Question question=new Question();                                       // QuestionEntity другая логика поля level
        question.setId(questionRequest.getId());
        question.setTitle(questionRequest.getTitle());
        question.setBody(questionRequest.getBody());
        question.setLevel(questionRequest.getLevel());
        question.setTags(questionRequest.getTags());
        return question;
    }
}
