package ua.timonov.web.project.command;

import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.model.bet.Bet;
import ua.timonov.web.project.model.bet.BetStatus;
import ua.timonov.web.project.model.horse.HorseInRace;
import ua.timonov.web.project.service.BetService;
import ua.timonov.web.project.service.HorseInRaceService;
import ua.timonov.web.project.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetUserBetsAction extends Action {

    public static final String USER_BETS = "userBets";

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private BetService betService = serviceFactory.createBetService();
    private HorseInRaceService horseInRaceService = serviceFactory.createHorseInRaceService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ParsingException, ServiceException {
        Long userId = Long.valueOf(request.getParameter("userId"));
        List<Bet> userBets = betService.findListByUser(userId);
        List<HorseInRace> listBetHorses = horseInRaceService.findBetHorsesInRace(userBets);
        request.setAttribute("userBets", userBets);
        request.setAttribute("listBetHorses", listBetHorses);
        request.setAttribute("betStatusPaid", BetStatus.PAID);
        return CONFIG.getString(USER_BETS);
    }
}
