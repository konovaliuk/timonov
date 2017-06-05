package ua.timonov.web.project.taglib;

import org.apache.log4j.Logger;
import ua.timonov.web.project.exception.AppException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class GreetingTag extends TagSupport {

    private static final Logger LOGGER = Logger.getLogger(GreetingTag.class);

    private String userName;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().write(userName);
        } catch (Exception e) {
            LOGGER.error("Error while using GreetingTag");
            throw new AppException("Error while using GreetingTag");
        }
        return SKIP_BODY;
    }
}
