package ru.atott.combiq.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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
import ru.atott.combiq.web.security.AuthService;
import ru.atott.combiq.web.view.QuestionViewBuilder;

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
                             @RequestParam(value = "dsl", required = false) String dsl) {
        GetQuestionContext context = new GetQuestionContext();
        context.setUserId(authService.getUserId());
        context.setId(questionId);

        if (index != null) {
            context.setProposedIndexInDslResponse(index);
            context.setDsl(DslParser.parse(dsl));
        }

        GetQuestionResponse questionResponse = getQuestionService.getQuestion(context);

        QuestionViewBuilder viewBuilder = new QuestionViewBuilder();
        viewBuilder.setQuestion(questionResponse.getQuestion());
        viewBuilder.setPositionInDsl(questionResponse.getPositionInDsl());
        viewBuilder.setDsl(dsl);
        viewBuilder.setTags(tagService.getTags(questionResponse.getQuestion().getTags()));
        return viewBuilder.build();
    }

    @RequestMapping(value = "/questions/{questionId}/content", method = RequestMethod.POST)
    @ResponseBody
    @Secured("sa")
    public Object postContent(@PathVariable("questionId") String questionId,
                              @RequestBody ContentRequest contentRequest) {
        questionService.saveQuestionBody(questionId, contentRequest.getContent());
        return new SuccessBean();
    }

    @RequestMapping(value = "/questions/{questionId}/comment", method = RequestMethod.POST)
    @ResponseBody
    @Secured("user")
    public Object postComment(@PathVariable("questionId") String questionId,
                              @RequestBody ContentRequest contentRequest) {
        questionService.saveComment(getContext(), questionId, contentRequest.getContent());
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
