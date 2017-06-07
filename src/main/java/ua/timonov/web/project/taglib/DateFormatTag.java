package ua.timonov.web.project.taglib;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Custom tag for output date in JSP pages
 */
public class DateFormatTag extends TagSupport {
    private static final Logger LOGGER = Logger.getLogger(DateFormatTag.class);
    public static final String DATE_FORMAT_TAG_ERROR = "Error while using DateFormatTag";

    private String format;
    private Date date;

    public void setFormat(String format) {
        this.format = format;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int doStartTag() throws JspException {
        SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
        String formattedDate = simpleFormat.format(date);
        try {
            pageContext.getOut().write(formattedDate);
        } catch (Exception e) {
            LOGGER.error(DATE_FORMAT_TAG_ERROR);
        }
        return SKIP_BODY;
    }
}
