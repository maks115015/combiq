package ru.atott.combiq.web.utils;

import org.apache.commons.lang3.StringUtils;
import ru.atott.combiq.service.UrlResolver;
import ru.atott.combiq.service.bean.Question;

import javax.servlet.http.HttpServletRequest;

public class RequestUrlResolver extends HostPortUrlResolver {

    public RequestUrlResolver(HttpServletRequest request) {
        super(request.getServerName(), request.getServerPort());
    }
}
