package ru.atott.combiq.service.question;

import ru.atott.combiq.service.question.impl.GetQuestionContext;
import ru.atott.combiq.service.question.impl.GetQuestionResponse;
import ru.atott.combiq.service.question.impl.SearchContext;
import ru.atott.combiq.service.question.impl.SearchResponse;

public interface GetQuestionService {

    SearchResponse getQuestions(SearchContext context);

    GetQuestionResponse getQuestion(GetQuestionContext context);
}
