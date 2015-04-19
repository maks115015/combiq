package ru.atott.combiq.web.controller;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.atott.combiq.service.bean.Question;
import ru.atott.combiq.service.question.GetQuestionContext;
import ru.atott.combiq.service.question.GetQuestionResponse;
import ru.atott.combiq.service.question.GetQuestionService;
import ru.atott.combiq.web.bean.PagingBean;
import ru.atott.combiq.web.bean.PagingBeanBuilder;
import ru.atott.combiq.web.view.QuestionsViewBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class QuestionController extends BaseController {
    @Autowired
    private GetQuestionService getQuestionService;
    private PagingBeanBuilder pagingBeanBuilder = new PagingBeanBuilder();

    @RequestMapping(value = "/questions", method = RequestMethod.GET)
    public ModelAndView questions(HttpServletRequest request,
                                  @RequestParam(defaultValue = "1") int page) {
        GetQuestionContext context = new GetQuestionContext();
        context.setPage(Math.max(0, page - 1));
        context.setSize(20);

        GetQuestionResponse questionsResponse = getQuestionService.getQuestions(context);

        PagingBean paging = pagingBeanBuilder.build(questionsResponse.getQuestions(), context.getPage(), request);
        List<Question> questions = questionsResponse.getQuestions().getContent();

        QuestionsViewBuilder viewBuilder = new QuestionsViewBuilder();
        viewBuilder.setQuestions(questions);
        viewBuilder.setPaging(paging);
        return viewBuilder.build();
    }

    @RequestMapping(value = "/questions/tagged/{tags}")
    public ModelAndView taggedQuestions(HttpServletRequest request,
                                        @RequestParam(defaultValue = "1") int page,
                                        @PathVariable("tags") String tags) {
        GetQuestionContext context = new GetQuestionContext();
        context.setPage(Math.max(0, page - 1));
        context.setSize(20);
        context.setTags(Lists.newArrayList(StringUtils.split(tags, ',')));

        GetQuestionResponse questionsResponse = getQuestionService.getQuestions(context);

        PagingBean paging = pagingBeanBuilder.build(questionsResponse.getQuestions(), context.getPage(), request);
        List<Question> questions = questionsResponse.getQuestions().getContent();

        QuestionsViewBuilder viewBuilder = new QuestionsViewBuilder();
        viewBuilder.setQuestions(questions);
        viewBuilder.setPaging(paging);
        return viewBuilder.build();
    }
}
