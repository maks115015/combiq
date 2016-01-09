package ru.atott.combiq.service.question;

import ru.atott.combiq.service.bean.Question;
import ru.atott.combiq.service.question.impl.GetQuestionContext;
import ru.atott.combiq.service.question.impl.GetQuestionResponse;
import ru.atott.combiq.service.question.impl.SearchContext;
import ru.atott.combiq.service.question.impl.SearchResponse;

import java.util.Optional;

public interface GetQuestionService {

    SearchResponse getQuestions(SearchContext context);

    Optional<SearchResponse> getAnotherQuestions(Question question);

    GetQuestionResponse getQuestion(GetQuestionContext context);
}
