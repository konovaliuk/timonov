package ua.timonov.web.project.controller.filter;

import ua.timonov.web.project.util.Pages;
import ua.timonov.web.project.util.Strings;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Manages access to secured part: if user is not logged he can visit only allowed pages
 */
public class SignedInFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        boolean allowedRequest = isAllowedRequest(request);
        if (!allowedRequest && (session == null || session.getAttribute(Strings.LOGGED_USER) == null)) {
            res.sendRedirect(Pages.INDEX_PAGE);
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean isAllowedRequest(ServletRequest request) {
        String action = request.getParameter(Strings.ACTION);
        if (action != null && (action.equals(Strings.SIGN_IN) || action.equals(Strings.SIGN_UP) ||
                action.equals(Strings.SIGN_UP_FORM))) {
            return true;
        }
        return false;
    }

    @Override
    public void destroy() {

    }
}
