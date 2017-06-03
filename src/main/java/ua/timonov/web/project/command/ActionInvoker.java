package ua.timonov.web.project.command;

import ua.timonov.web.project.command.authorizing.*;
import ua.timonov.web.project.command.bet.*;
import ua.timonov.web.project.command.horse.*;
import ua.timonov.web.project.command.horseinrace.*;
import ua.timonov.web.project.command.odds.*;
import ua.timonov.web.project.command.race.*;
import ua.timonov.web.project.command.user.*;
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
        actionMap.put("signIn", new SignInAction());
        actionMap.put("getSignUpForm", new GetSignUpAction());
        actionMap.put("signUp", new SignUpAction());
        actionMap.put("logout", new LogoutAction());

        actionMap.put("horses", new GetHorsesAction());
        actionMap.put("horse", new GetHorseAction());
        actionMap.put("horseEdit", new EditHorseAction());
        actionMap.put("horseDelete", new DeleteHorseAction());
        actionMap.put("horseAdd", new AddHorseAction());
        actionMap.put("horseSaveEdited", new SaveEditedHorseAction());
        actionMap.put("racesByHorse", new GetRacesByHorseAction());

        actionMap.put("users", new GetUsersAction());
        actionMap.put("user", new GetUserAction());
        actionMap.put("userEdit", new EditUserAction());
        actionMap.put("userDelete", new DeleteUserAction());
        actionMap.put("userAdd", new AddUserAction());
        actionMap.put("userSaveEdited", new SaveEditedUserAction());

        actionMap.put("races", new GetRacesAction());
        actionMap.put("race", new GetRaceAction());
        actionMap.put("raceAdd", new SaveRaceAction());
        actionMap.put("raceEdit", new EditRaceAction());
        actionMap.put("wonBets", new GetWonBets());
        actionMap.put("raceDelete", new DeleteRaceAction());

        actionMap.put("raceSaveEditedAttributes", new SaveEditedRaceAttributesAction());
        actionMap.put("racePlacesSave", new SaveRacePlacesAction());
        actionMap.put("horseInRace", new GetHorseInRaceAction());
        actionMap.put("horseInRaceBookie", new GetHorseInRaceBookieAction());
        actionMap.put("horseInRaceDelete", new DeleteHorseInRaceAction());
        actionMap.put("horseInRaceAdd", new AddHorseInRaceAction());
        actionMap.put("cancelRace", new CancelRaceAction());
        actionMap.put("changeRaceStatus", new ChangeRaceStatus());

        actionMap.put("makeBet", new MakeBetAction());
        actionMap.put("oddsSaveEdited", new SaveEditedOddsAction());
        actionMap.put("oddsAdd", new AddOddsAction());
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
