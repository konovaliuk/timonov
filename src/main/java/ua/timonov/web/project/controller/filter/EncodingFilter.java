package ua.timonov.web.project.controller.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * sets encoding to UTF-8
 */
public class EncodingFilter implements Filter {

    public static final String ENCODING = "utf-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(ENCODING);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
