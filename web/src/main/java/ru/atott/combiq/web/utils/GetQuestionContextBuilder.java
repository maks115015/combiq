package ru.atott.combiq.web.utils;

import org.springframework.stereotype.Component;
import ru.atott.combiq.service.dsl.DslParser;
import ru.atott.combiq.service.dsl.DslQuery;
import ru.atott.combiq.service.dsl.DslTag;
import ru.atott.combiq.service.question.GetQuestionContext;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetQuestionContextBuilder {
    private static int size = 20;

    public GetQuestionContext list(int page) {
        GetQuestionContext context = new GetQuestionContext();
        context.setPage(page);
        context.setSize(size);
        context.setDslQuery(new DslQuery());
        return context;
    }

    public GetQuestionContext listByTags(int page, List<String> tags) {
        DslQuery query = new DslQuery();
        query.setTags(tags.stream().map(DslTag::new).collect(Collectors.toList()));

        GetQuestionContext context = new GetQuestionContext();
        context.setPage(page);
        context.setSize(size);
        context.setDslQuery(query);
        return context;
    }

    public GetQuestionContext listByLevel(int page, String level) {
        DslQuery query = new DslQuery();
        query.setLevel(level);

        GetQuestionContext context = new GetQuestionContext();
        context.setPage(page);
        context.setSize(size);
        context.setDslQuery(query);
        return context;
    }

    public GetQuestionContext listByDsl(int page, String dsl) {
        DslQuery dslQuery = DslParser.parse(dsl);

        GetQuestionContext context = new GetQuestionContext();
        context.setPage(page);
        context.setSize(size);
        context.setDslQuery(dslQuery);
        return context;
    }
}
