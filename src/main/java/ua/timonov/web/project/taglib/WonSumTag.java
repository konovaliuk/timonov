package ua.timonov.web.project.taglib;

import org.apache.log4j.Logger;
import ua.timonov.web.project.model.bet.Bet;
import ua.timonov.web.project.model.user.Money;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Custom tag for output multiplied money sum
 */
public class WonSumTag extends TagSupport {

    private static final Logger LOGGER = Logger.getLogger(WonSumTag.class);
    public static final String WON_SUM_TAG_ERROR = "Error while using WonSumTag";

    private Bet betWonSum;

    public void setBetWonSum(Bet betWonSum) {
        this.betWonSum = betWonSum;
    }

    @Override
    public int doStartTag() throws JspException {
        Money wonSum = betWonSum.getSum().multiply(betWonSum.getOdds().getOddsValue());
        try {
            pageContext.getOut().write(wonSum.getValue().toString());
        } catch (Exception e) {
            LOGGER.error(WON_SUM_TAG_ERROR);
        }
        return SKIP_BODY;
    }
}