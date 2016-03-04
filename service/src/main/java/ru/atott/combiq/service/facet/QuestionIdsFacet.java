package ru.atott.combiq.service.facet;

import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import ru.atott.combiq.dao.Types;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class QuestionIdsFacet implements Facet {

    private List<String> questionIds;

    public QuestionIdsFacet(String questionId) {
        this.questionIds = Collections.singletonList(questionId);
    }

    public QuestionIdsFacet(List<String> questionIds) {
        this.questionIds = questionIds;
    }

    public List<String> getQuestionIds() {
        return questionIds;
    }

    @Override
    public Optional<FilterBuilder> getFilter(FacetContext context) {
        return Optional.of(
                FilterBuilders
                    .idsFilter(Types.question)
                    .ids(questionIds.toArray(new String[questionIds.size()])));
    }
}
