package ru.atott.combiq.web.utils;

import org.apache.commons.lang3.StringUtils;
import ru.atott.combiq.service.UrlResolver;

import javax.servlet.http.HttpServletRequest;

public class RequestUrlResolver implements UrlResolver {
    private HttpServletRequest request;

    public RequestUrlResolver(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public String externalize(String relativeUrl) {
        if (!StringUtils.startsWith(relativeUrl, "/")) {
            throw new RuntimeException("Url must be started from /");
        }

        StringBuilder result = new StringBuilder();
        result.append("http://");
        result.append(request.getServerName());

        if (request.getServerPort() != 80) {
            result.append(":");
            result.append(request.getServerPort());
        }

        result.append(relativeUrl);
        return result.toString();
    }
}
