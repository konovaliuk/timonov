package ua.timonov.web.project.taglib;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class GreetingTag extends TagSupport {

    private static final Logger LOGGER = Logger.getLogger(GreetingTag.class);
    public static final String GREETING_TAG_ERROR = "Error while using GreetingTag";

    private String userName;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().write(userName);
        } catch (Exception e) {
            LOGGER.error(GREETING_TAG_ERROR);
        }
        return SKIP_BODY;
    }
}
