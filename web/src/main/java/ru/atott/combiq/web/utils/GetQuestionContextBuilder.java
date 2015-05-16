package ru.atott.combiq.web.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.atott.combiq.service.dsl.DslParser;
import ru.atott.combiq.service.dsl.DslQuery;
import ru.atott.combiq.service.dsl.DslTag;
import ru.atott.combiq.service.question.impl.SearchContext;
import ru.atott.combiq.web.security.AuthService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetQuestionContextBuilder {
    private static int size = 20;
    @Autowired
    private AuthService authService;

    public SearchContext list(int page) {
        SearchContext context = new SearchContext();
        context.setFrom(page * size);
        context.setSize(size);
        context.setDslQuery(new DslQuery());
        context.setUserId(authService.getUserId());
        return context;
    }

    public SearchContext listByTags(int page, List<String> tags) {
        DslQuery query = new DslQuery();
        query.setTags(tags.stream().map(DslTag::new).collect(Collectors.toList()));

        SearchContext context = new SearchContext();
        context.setFrom(page * size);
        context.setSize(size);
        context.setDslQuery(query);
        context.setUserId(authService.getUserId());
        return context;
    }

    public SearchContext listByLevel(int page, String level) {
        DslQuery query = new DslQuery();
        query.setLevel(level);

        SearchContext context = new SearchContext();
        context.setFrom(page * size);
        context.setSize(size);
        context.setDslQuery(query);
        context.setUserId(authService.getUserId());
        return context;
    }

    public SearchContext listByDsl(int page, String dsl) {
        DslQuery dslQuery = DslParser.parse(dsl);

        SearchContext context = new SearchContext();
        context.setFrom(page * size);
        context.setSize(size);
        context.setDslQuery(dslQuery);
        context.setUserId(authService.getUserId());
        return context;
    }
}
