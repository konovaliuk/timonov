package ua.timonov.web.project.command;

import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.exception.ServiceLayerException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class ActionInvoker {

    private Map<String, Action> actionMap = new HashMap<>();

    private ActionInvoker() {
        actionMap.put("default", new DefaultAction());
        actionMap.put("login", new LoginAction());
        actionMap.put("horses", new GetHorsesAction());
        actionMap.put("horse", new GetHorseAction());
        actionMap.put("races", new GetRacesAction());
        actionMap.put("race", new GetRaceHorsesAction());
        actionMap.put("raceEdit", new EditRaceAction());
        actionMap.put("raceSaveEdited", new SaveEditedRaceAction());
        actionMap.put("racePlacesSave", new SaveRacePlacesAction());
        actionMap.put("horseInRace", new GetHorseInRaceAction());
        actionMap.put("horseInRaceBookie", new GetHorseInRaceBookieAction());
        actionMap.put("makeBet", new MakeBetAction());
        actionMap.put("oddsSave", new SaveOddsAction());
        actionMap.put("oddsEdit", new EditOddsAction());
        actionMap.put("oddsDelete", new DeleteOddsAction());
        actionMap.put("error", new ErrorAction());
    }

    private static class Holder {
        private static final ActionInvoker instance = new ActionInvoker();
    }

    public static ActionInvoker getInstance() {
        return Holder.instance;
    }

    public String invoke(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParsingException, ServiceLayerException {
        String actionName = request.getParameter("action");
        Action action = actionMap.get(actionName);
        if (action == null) {
            action = new DefaultAction();
        }
        return action.execute(request, response);
    }

}
