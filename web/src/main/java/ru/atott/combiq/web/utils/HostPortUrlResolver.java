package ru.atott.combiq.web.utils;

import org.apache.commons.lang3.StringUtils;
import ru.atott.combiq.service.UrlResolver;
import ru.atott.combiq.service.bean.Question;
import ru.atott.combiq.service.bean.QuestionnaireHead;

public class HostPortUrlResolver implements UrlResolver {

    private String host;

    private int port;

    public HostPortUrlResolver(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public HostPortUrlResolver(String host) {
        this(host, 80);
    }

    public HostPortUrlResolver() {
        this("combiq.ru", 80);
    }

    @Override
    public String externalize(String relativeUrl) {
        if (!StringUtils.startsWith(relativeUrl, "/")) {
            throw new RuntimeException("Url must be started from /");
        }

        StringBuilder result = new StringBuilder();
        result.append("http://");
        result.append(host);

        if (port != 80) {
            result.append(":");
            result.append(port);
        }

        result.append(relativeUrl);
        return result.toString();
    }

    @Override
    public String getQuestionUrl(Question question) {
        return getQuestionUrl(question, null);
    }

    @Override
    public String getQuestionUrl(Question question, String queryString) {
        if (StringUtils.isBlank(queryString)) {
            return "/questions/" + question.getId();
        } else {
            return "/questions/" + question.getId() + "?" + queryString;
        }
    }

    @Override
    public String getQuestionnaireUrl(QuestionnaireHead questionnaire) {
        return getQuestionnaireUrl(questionnaire, null);
    }

    @Override
    public String getQuestionnaireUrl(QuestionnaireHead questionnaire, String queryString) {
        if (StringUtils.isBlank(queryString)) {
            return "/questionnaire/" + questionnaire.getId();
        } else {
            return "/questionnaire/" + questionnaire.getId() + "?" + queryString;
        }
    }
}
