package ru.atott.combiq.service;

import ru.atott.combiq.service.bean.Question;
import ru.atott.combiq.service.bean.QuestionnaireHead;

public interface UrlResolver {

    String externalize(String relativeUrl);

    String getQuestionUrl(Question question);

    String getQuestionUrl(Question question, String queryString);

    String getQuestionnaireUrl(QuestionnaireHead questionnaire);

    String getQuestionnaireUrl(QuestionnaireHead questionnaire, String queryString);
}
