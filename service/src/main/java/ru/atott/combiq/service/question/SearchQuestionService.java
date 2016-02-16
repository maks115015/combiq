package ru.atott.combiq.service.question;

import ru.atott.combiq.service.bean.Question;
import ru.atott.combiq.service.question.impl.GetQuestionContext;
import ru.atott.combiq.service.question.impl.GetQuestionResponse;
import ru.atott.combiq.service.question.impl.SearchContext;
import ru.atott.combiq.service.question.impl.SearchResponse;

import java.util.List;
import java.util.Optional;

public interface SearchQuestionService {

    SearchResponse searchQuestions(SearchContext context);

    Optional<SearchResponse> searchAnotherQuestions(Question question);

    GetQuestionResponse getQuestion(GetQuestionContext context);

    Question getQuestionByLegacyId(String legacyId);

    List<Question> getQuestionsWithLatestComments(int count);

    List<Question> get3QuestionsWithLatestComments();

    List<Question> get7QuestionsWithLatestComments();
}
