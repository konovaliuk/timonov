package ua.timonov.web.project.command.odds;

import org.apache.log4j.Logger;
import ua.timonov.web.project.command.horseinrace.GetHorseInRaceBookieAction;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.model.bet.Odds;
import ua.timonov.web.project.parser.Parser;
import ua.timonov.web.project.parser.ParserFactory;
import ua.timonov.web.project.service.OddsService;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.util.LoggerMessages;
import ua.timonov.web.project.util.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * deletes bet odds
 */
public class DeleteOddsAction extends GetHorseInRaceBookieAction {

    private static final Logger LOGGER = Logger.getLogger(DeleteOddsAction.class);

    private OddsService oddsService = ServiceFactory.getInstance().createOddsService();
    private Parser<Long> idParser = ParserFactory.createIdParser();
    private Odds odds;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        Long oddsId = idParser.parse(request.getParameter(Strings.ODDS_ID), Strings.ODDS_ID);
        odds = oddsService.findById(oddsId);
        oddsService.delete(oddsId);
        LOGGER.info(LoggerMessages.ODDS_DELETED);
        request.setAttribute(Strings.MESSAGE_SUCCESS, true);
        return prepareHorseInRacePage(request, odds.getHorseInRaceId());
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) throws AppException {
        LOGGER.warn(LoggerMessages.ERROR_DElETE_ODDS);
        request.setAttribute(Strings.MESSAGE_ERROR, e.getMessage());
        request.setAttribute(Strings.ERROR_DETAILS, e.getCause());
        return prepareHorseInRacePage(request, odds.getHorseInRaceId());
    }
}