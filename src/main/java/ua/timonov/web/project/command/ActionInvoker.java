package ua.timonov.web.project.command;

import ua.timonov.web.project.command.authorize.GetSignUpAction;
import ua.timonov.web.project.command.authorize.LogoutAction;
import ua.timonov.web.project.command.authorize.SignInAction;
import ua.timonov.web.project.command.authorize.SignUpAction;
import ua.timonov.web.project.command.bet.CancelBet;
import ua.timonov.web.project.command.bet.GetWonBets;
import ua.timonov.web.project.command.bet.MakeBetAction;
import ua.timonov.web.project.command.horse.*;
import ua.timonov.web.project.command.horseinrace.AddHorseInRaceAction;
import ua.timonov.web.project.command.horseinrace.DeleteHorseInRaceAction;
import ua.timonov.web.project.command.horseinrace.GetHorseInRaceAction;
import ua.timonov.web.project.command.horseinrace.GetHorseInRaceBookieAction;
import ua.timonov.web.project.command.odds.AddOddsAction;
import ua.timonov.web.project.command.odds.DeleteOddsAction;
import ua.timonov.web.project.command.odds.EditOddsAction;
import ua.timonov.web.project.command.odds.SaveEditedOddsAction;
import ua.timonov.web.project.command.race.*;
import ua.timonov.web.project.command.user.*;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.util.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Retrieves action property from from request and takes appropriate action from Map
 */
public class ActionInvoker {

    private static final ActionInvoker instance = new ActionInvoker();

    private Map<String, Action> actionMap = new HashMap<>();

    private ActionInvoker() {
        actionMap.put("main", new GetMainPageAction());
        actionMap.put("setLang", new SetLanguageAction());
        actionMap.put("setLangIndex", new SetLanguageIndexAction());

        actionMap.put("signIn", new SignInAction());
        actionMap.put("getSignUpForm", new GetSignUpAction());
        actionMap.put("signUp", new SignUpAction());
        actionMap.put("logout", new LogoutAction());

        actionMap.put("horses", new GetHorsesAction());
        actionMap.put("horseEdit", new EditHorseAction());
        actionMap.put("horseDelete", new DeleteHorseAction());
        actionMap.put("horseAdd", new AddHorseAction());
        actionMap.put("horseSaveEdited", new SaveEditedHorseAction());
        actionMap.put("racesByHorse", new GetRacesByHorseAction());

        actionMap.put("users", new GetUsersAction());
        actionMap.put("userEdit", new EditUserAction());
        actionMap.put("userDelete", new DeleteUserAction());
        actionMap.put("userAdd", new AddUserAction());
        actionMap.put("userSaveEdited", new SaveEditedUserAction());

        actionMap.put("races", new GetRacesAction());
        actionMap.put("race", new GetRaceAction());
        actionMap.put("raceAdd", new SaveRaceAction());
        actionMap.put("raceManage", new ManageRaceAction());
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

        actionMap.put("userBets", new GetUserBetsAction());
        actionMap.put("cancelBet", new CancelBet());
    }

    public static ActionInvoker getInstance() {
        return instance;
    }

    public String invoke(HttpServletRequest request, HttpServletResponse response) {
        String actionName = request.getParameter(Strings.ACTION);
        Action action = actionMap.get(actionName);
        if (action == null) {
            action = new DefaultAction();
        }
        try {
            return action.execute(request, response);
        } catch (AppException e) {
            return action.handleError(request, e);
        }
    }
}
