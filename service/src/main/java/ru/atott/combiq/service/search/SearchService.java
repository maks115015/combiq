package ru.atott.combiq.service.search;

import ru.atott.combiq.service.bean.Question;
import ru.atott.combiq.service.question.impl.GetQuestionContext;
import ru.atott.combiq.service.question.impl.GetQuestionResponse;

import java.util.List;
import java.util.Optional;

public interface SearchService {

    SearchResponse searchQuestions(SearchContext context);

    long countQuestions(SearchContext context);

    Optional<SearchResponse> searchAnotherQuestions(Question question);

    GetQuestionResponse getQuestion(GetQuestionContext context);

    Question getQuestionByLegacyId(String legacyId);

    List<Question> getQuestionsWithLatestComments(int count);

    List<Question> get3QuestionsWithLatestComments();

    List<Question> get7QuestionsWithLatestComments();
}
