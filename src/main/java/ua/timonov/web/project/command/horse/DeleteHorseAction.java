package ua.timonov.web.project.command.horse;

import org.apache.log4j.Logger;
import ua.timonov.web.project.exception.AppException;
import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.exception.ServiceException;
import ua.timonov.web.project.service.HorseService;
import ua.timonov.web.project.service.ServiceFactory;
import ua.timonov.web.project.util.LoggerMessages;
import ua.timonov.web.project.util.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * deletes chosen horse
 */
public class DeleteHorseAction extends GetHorsesAction {

    private static final Logger LOGGER = Logger.getLogger(DeleteHorseAction.class);

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private HorseService horseService = serviceFactory.createHorseService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ParsingException, ServiceException {
        Long horseId = Long.valueOf(request.getParameter(Strings.HORSE_ID));
        horseService.delete(horseId);
        LOGGER.info(LoggerMessages.HORSE_DELETED);
        request.setAttribute(Strings.MESSAGE_SUCCESS, true);
        return prepareHorsesPage(request);
    }

    @Override
    public String doOnError(HttpServletRequest request, Exception e) throws AppException {
        LOGGER.warn(LoggerMessages.ERROR_DELETING_HORSE);
        request.setAttribute(Strings.MESSAGE_ERROR, e.getMessage());
        request.setAttribute(Strings.ERROR_DETAILS, e.getStackTrace());
        return prepareHorsesPage(request);
    }
}
