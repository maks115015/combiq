package ru.atott.combiq.web.controller;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.atott.combiq.service.bean.Question;
import ru.atott.combiq.service.question.impl.SearchContext;
import ru.atott.combiq.service.question.impl.SearchResponse;
import ru.atott.combiq.service.question.SearchQuestionService;
import ru.atott.combiq.web.bean.CountQuestionSearchBean;
import ru.atott.combiq.web.bean.PagingBean;
import ru.atott.combiq.web.bean.PagingBeanBuilder;
import ru.atott.combiq.web.utils.SearchQuestionContextBuilder;
import ru.atott.combiq.web.view.SearchViewBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController extends BaseController {

    private PagingBeanBuilder pagingBeanBuilder = new PagingBeanBuilder();

    @Autowired
    private SearchQuestionService searchQuestionService;

    @Autowired
    private SearchQuestionContextBuilder searchQuestionContextBuilder;

    @RequestMapping(value = "/questions/search", method = RequestMethod.GET)
    public ModelAndView search(HttpServletRequest request,
                               @RequestParam(defaultValue = "1") int page,
                               @RequestParam(value = "q", defaultValue = "") String dsl) {
        page = getZeroBasedPage(page);
        SearchContext context = searchQuestionContextBuilder.listByDsl(page, dsl);
        return getView(request, context, dsl);
    }

    @ResponseBody
    @RequestMapping(value = "/questions/search/count", method = RequestMethod.GET)
    public Object countSearch(HttpServletRequest request,
                              @RequestParam(value = "q", defaultValue = "") String dsl) {
        SearchContext context = searchQuestionContextBuilder.listByDsl(0, dsl);
        long countQuestions = searchQuestionService.countQuestions(context);
        return new CountQuestionSearchBean(countQuestions);
    }

    @RequestMapping(value = "/questions", method = RequestMethod.GET)
    public ModelAndView questions(HttpServletRequest request,
                                  @RequestParam(defaultValue = "1") int page) {
        page = getZeroBasedPage(page);
        SearchContext context = searchQuestionContextBuilder.list(page);
        String subTitle = "Вопросы для подготовки к собеседованию Java";
        return getView(request, context, null, subTitle, true);
    }

    @RequestMapping(value = "/questions/tagged/{tags}")
    public ModelAndView taggedQuestions(HttpServletRequest request,
                                        @RequestParam(defaultValue = "1") int page,
                                        @PathVariable("tags") String tags) {
        page = getZeroBasedPage(page);
        ArrayList<String> tagsList = Lists.newArrayList(StringUtils.split(tags, ','));
        SearchContext context = searchQuestionContextBuilder.listByTags(page, tagsList);
        return getView(request, context, null);
    }

    @RequestMapping(value = "/questions/level/{level}")
    public ModelAndView level(HttpServletRequest request,
                              @RequestParam(defaultValue = "1") int page,
                              @PathVariable("level") String level) {
        page = getZeroBasedPage(page);
        SearchContext context = searchQuestionContextBuilder.listByLevel(page, level);
        return getView(request, context, null);
    }

    @RequestMapping(value = "/questions/deleted", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('sa','contenter')")
    public ModelAndView deleted(HttpServletRequest request,
                               @RequestParam(defaultValue = "1") int page,
                               @RequestParam(value = "q", defaultValue = "") String dsl) {
        page = getZeroBasedPage(page);
        SearchContext context = searchQuestionContextBuilder.listByDsl(page, dsl);
        context.getDslQuery().setVisibleDeleted(true);
        return getView(request, context, dsl);
    }

    @RequestMapping(value = "/{userId}/questions", method = RequestMethod.GET)
    public ModelAndView userQuestion(HttpServletRequest request,
                               @RequestParam(defaultValue = "1") int page,
                               @RequestParam(value = "q", defaultValue = "") String dsl,
                               @PathVariable("userId") String userId) {
        page = getZeroBasedPage(page);
        SearchContext context = searchQuestionContextBuilder.listByDsl(page, dsl);
        context.getDslQuery().setUserId(userId);
        if(!getContext().getUser().getUserId().equals(userId)){
            context.getDslQuery().setVisibleDeleted(true);
        }
        return getView(request, context, dsl);
    }

    private ModelAndView getView(HttpServletRequest request, SearchContext context, String dsl,
                                 String subTitle, boolean questionsCatalog) {
        SearchResponse questionsResponse = searchQuestionService.searchQuestions(context);

        PagingBean paging = pagingBeanBuilder.build(questionsResponse.getQuestions(), questionsResponse.getQuestions().getNumber(), request);
        List<Question> questions = questionsResponse.getQuestions().getContent();
        dsl = StringUtils.defaultIfBlank(dsl, context.getDslQuery().toDsl());

        SearchViewBuilder viewBuilder = new SearchViewBuilder();
        viewBuilder.setQuestions(questions);
        viewBuilder.setPaging(paging);
        viewBuilder.setDsl(dsl);
        viewBuilder.setPopularTags(questionsResponse.getPopularTags());
        viewBuilder.setSubTitle(subTitle);
        viewBuilder.setQuestionsCatalog(questionsCatalog);
        viewBuilder.setDslQuery(context.getDslQuery());
        return viewBuilder.build();
    }

    private ModelAndView getView(HttpServletRequest request, SearchContext context, String dsl) {
        return getView(request, context, dsl, null, false);
    }
}
