package ua.timonov.web.project.command;

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
        actionMap.put("races", new GetRacesAction());
        actionMap.put("horses", new GetHorsesAction());
        actionMap.put("race", new GetRaceHorsesAction());
        actionMap.put("horse_in_race", new GetHorseInRaceAction());
        actionMap.put("create_bet", new SaveBetAction());
        actionMap.put("save_bet", new GetSaveBetAction());
        actionMap.put("error", new ErrorAction());
    }

    private static class Holder {
        private static final ActionInvoker instance = new ActionInvoker();
    }

    public static ActionInvoker getInstance() {
        return Holder.instance;
    }

    public String invoke(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                                                                                          IOException {
        String actionName = request.getParameter("action");
        Action action = actionMap.get(actionName);
        if (action == null) {
            action = new DefaultAction();
        }
        return action.execute(request, response);
    }

}
