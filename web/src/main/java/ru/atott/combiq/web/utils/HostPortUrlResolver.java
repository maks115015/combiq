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
        return "/questions/" + question.getId();
    }

    @Override
    public String getQuestionnaireUrl(QuestionnaireHead questionnaire) {
        return "/questionnaire/" + questionnaire.getId();
    }
}
