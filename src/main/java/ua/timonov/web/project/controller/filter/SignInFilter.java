package ua.timonov.web.project.controller.filter;

import ua.timonov.web.project.util.Pages;
import ua.timonov.web.project.util.Strings;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages access to secured part: if user is not logged he can visit only allowed pages
 */
public class SignInFilter implements Filter {

    private List<String> allowedActions = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        allowedActions.add(Strings.SIGN_IN);
        allowedActions.add(Strings.SIGN_UP);
        allowedActions.add(Strings.SIGN_UP_FORM);
        allowedActions.add(Strings.SET_LANG_INDEX);
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
        if (action != null && allowedActions.contains(action)) {
            return true;
        }
        return false;
    }

    @Override
    public void destroy() {

    }
}
