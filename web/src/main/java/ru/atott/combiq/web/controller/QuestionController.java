package ru.atott.combiq.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.atott.combiq.service.UrlResolver;
import ru.atott.combiq.service.bean.Question;
import ru.atott.combiq.service.dsl.DslParser;
import ru.atott.combiq.service.question.GetQuestionService;
import ru.atott.combiq.service.question.QuestionReputationService;
import ru.atott.combiq.service.question.QuestionService;
import ru.atott.combiq.service.question.TagService;
import ru.atott.combiq.service.question.impl.GetQuestionContext;
import ru.atott.combiq.service.question.impl.GetQuestionResponse;
import ru.atott.combiq.web.bean.ReputationVoteBean;
import ru.atott.combiq.web.bean.SuccessBean;
import ru.atott.combiq.web.request.ContentRequest;
import ru.atott.combiq.web.request.EditCommentRequest;
import ru.atott.combiq.web.security.AuthService;
import ru.atott.combiq.web.utils.RequestUrlResolver;
import ru.atott.combiq.web.view.QuestionViewBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class QuestionController extends BaseController {
    @Autowired
    private AuthService authService;
    @Autowired
    private GetQuestionService getQuestionService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionReputationService questionReputationService;
    @Autowired
    private TagService tagService;

    @RequestMapping(value = "/questions/{questionId}")
    public ModelAndView view(@PathVariable("questionId") String questionId,
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

        GetQuestionResponse questionResponse = getQuestionService.getQuestion(context);

        List<Question> anotherQuestions = null;
        if (questionResponse.getQuestion().isLanding()) {
            anotherQuestions = getQuestionService
                    .getAnotherQuestions(questionResponse.getQuestion())
                    .map(response -> response.getQuestions().getContent())
                    .orElse(null);
        }

        QuestionViewBuilder viewBuilder = new QuestionViewBuilder();
        viewBuilder.setQuestion(questionResponse.getQuestion());
        viewBuilder.setPositionInDsl(questionResponse.getPositionInDsl());
        viewBuilder.setDsl(dsl);
        viewBuilder.setTags(tagService.getTags(questionResponse.getQuestion().getTags()));
        viewBuilder.setCanonicalUrl(urlResolver.externalize("/questions/" + questionId));
        viewBuilder.setAnotherQuestions(anotherQuestions);
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

    @RequestMapping(value = "/questions/commentSave", method = RequestMethod.POST)
    @ResponseBody
    @Deprecated
    public SuccessBean saveComment(@RequestParam(value = "comment", required = false) String comment,
                                   @RequestParam(value = "questionId", required = true) String questionId) {
        if (authService.getUser() == null) {
            return new SuccessBean(false);
        }

        questionService.saveUserComment(authService.getUserId(), questionId, comment);
        return new SuccessBean();
    }

    @RequestMapping(value = "/questions/reputationVote", method = RequestMethod.POST)
    @ResponseBody
    @Deprecated
    public ReputationVoteBean reputationVote(@RequestParam(value = "up", required = true) boolean up,
                                             @RequestParam(value = "questionId", required = true) String questionId) {
        if (authService.getUser() == null) {
            return null;
        }

        ReputationVoteBean result = new ReputationVoteBean();
        result.setQuestionId(questionId);

        if (up) {
            result.setUserReputation(1);
            result.setQuestionReputation(questionReputationService.voteUp(authService.getUserId(), questionId));
        } else {
            result.setUserReputation(-1);
            result.setQuestionReputation(questionReputationService.voteDown(authService.getUserId(), questionId));
        }

        return result;
    }
}
