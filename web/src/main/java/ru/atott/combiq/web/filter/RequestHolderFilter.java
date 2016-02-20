package ru.atott.combiq.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @deprecated Перейти на использование spring-ового холдера,
 * http://stackoverflow.com/questions/3320674/spring-how-do-i-inject-an-httpservletrequest-into-a-request-scoped-bean
 */
@Deprecated
public class RequestHolderFilter implements Filter {

    public static ThreadLocal<HttpServletRequest> REQUEST = new ThreadLocal<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        REQUEST.set((HttpServletRequest) servletRequest);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
