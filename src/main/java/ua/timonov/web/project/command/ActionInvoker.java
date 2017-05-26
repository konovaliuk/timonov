package ua.timonov.web.project.command;

import ua.timonov.web.project.command.bet.MakeBetAction;
import ua.timonov.web.project.command.horse.GetHorseAction;
import ua.timonov.web.project.command.horse.GetHorsesAction;
import ua.timonov.web.project.command.horseinrace.DeleteHorseInRaceAction;
import ua.timonov.web.project.command.horseinrace.GetHorseInRaceAction;
import ua.timonov.web.project.command.horseinrace.GetHorseInRaceBookieAction;
import ua.timonov.web.project.command.odds.DeleteOddsAction;
import ua.timonov.web.project.command.odds.EditOddsAction;
import ua.timonov.web.project.command.odds.SaveOddsAction;
import ua.timonov.web.project.command.race.*;
import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class ActionInvoker {

    private static final ActionInvoker instance = new ActionInvoker();

    private Map<String, Action> actionMap = new HashMap<>();

    private ActionInvoker() {
        actionMap.put("home", new DefaultAction());
        actionMap.put("login", new LoginAction());
        actionMap.put("horses", new GetHorsesAction());
        actionMap.put("horse", new GetHorseAction());
        actionMap.put("races", new GetRacesAction());
        actionMap.put("race", new GetRaceAction());
        actionMap.put("raceEdit", new EditRaceAction());
        actionMap.put("raceSaveEdited", new SaveEditedRaceAction());
        actionMap.put("racePlacesSave", new SaveRacePlacesAction());
        actionMap.put("horseInRace", new GetHorseInRaceAction());
        actionMap.put("horseInRaceBookie", new GetHorseInRaceBookieAction());
        actionMap.put("horseInRaceDelete", new DeleteHorseInRaceAction());

        actionMap.put("makeBet", new MakeBetAction());
        actionMap.put("oddsSave", new SaveOddsAction());
        actionMap.put("oddsEdit", new EditOddsAction());
        actionMap.put("oddsDelete", new DeleteOddsAction());
        actionMap.put("error", new ErrorAction());
    }

    public static ActionInvoker getInstance() {
        return instance;
    }

    public String invoke(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParsingException, ServiceException {
        String actionName = request.getParameter("action");
        Action action = actionMap.get(actionName);
        if (action == null) {
            action = new DefaultAction();
        }
        return action.execute(request, response);
    }

}
