package ua.timonov.web.project.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SignedInFilter implements Filter {

    public static final String SIGN_IN = "signIn";
    public static final String SIGN_UP = "signUp";
    public static final String SIGN_UP_FORM = "getSignUpForm";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        boolean allowedRequest = isAllowedRequest(request);
        if (!allowedRequest && (session == null || session.getAttribute("user") == null)) {
            res.sendRedirect("index.jsp");
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean isAllowedRequest(ServletRequest request) {
        String action = request.getParameter("action");
        if (action != null && (action.equals(SIGN_IN) || action.equals(SIGN_UP) ||
                action.equals(SIGN_UP_FORM))) {
            return true;
        }
        return false;
    }

    @Override
    public void destroy() {

    }
}
