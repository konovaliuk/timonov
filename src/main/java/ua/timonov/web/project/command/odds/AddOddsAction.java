package ua.timonov.web.project.command.odds;

import org.apache.log4j.Logger;
import ua.timonov.web.project.command.horseinrace.GetHorseInRaceBookieAction;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.model.bet.BetType;
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
 * saves new bet odds value
 */
public class AddOddsAction extends GetHorseInRaceBookieAction {

    private static final Logger LOGGER = Logger.getLogger(AddOddsAction.class);

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private OddsService oddsService = serviceFactory.createOddsService();
    private Parser<Long> idParser = ParserFactory.createIdParser();
    private Parser<Integer> integerParser = ParserFactory.createIntegerParser();
    private Odds odds;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        odds = createOddsFromRequest(request);
        oddsService.validateOddsRates(odds);
        oddsService.save(odds);
        request.setAttribute(Strings.MESSAGE_SUCCESS, true);
        LOGGER.info(LoggerMessages.ODDS_ADDED);
        return prepareHorseInRacePage(request, odds.getHorseInRaceId());
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) throws AppException {
        LOGGER.info(LoggerMessages.ERROR_ADD_ODDS);
        request.setAttribute(Strings.MESSAGE_ERROR, e.getMessage());
        request.setAttribute(Strings.ERROR_DETAILS, e.getStackTrace());
        request.setAttribute(Strings.ODDS_WITH_INPUT_ERROR, odds);
        return prepareHorseInRacePage(request, odds.getHorseInRaceId());
    }

    private Odds createOddsFromRequest(HttpServletRequest request) throws ParsingException {
        String parameterId = request.getParameter(Strings.ODDS_ID);
        Long id = parameterId != null ? idParser.parse(parameterId, Strings.ODDS_ID) : 0L;
        Long horseInRaceId = idParser.parse(request.getParameter(Strings.HORSE_IN_RACE_ID), Strings.HORSE_IN_RACE_ID);
        BetType betType = BetType.valueOf(request.getParameter(Strings.BET_TYPE));
        int total = integerParser.parse(request.getParameter(Strings.TOTAL), Strings.TOTAL);
        int chances = integerParser.parse(request.getParameter(Strings.CHANCES), Strings.CHANCES);
        return new Odds(id, horseInRaceId, betType, total, chances);
    }
}
