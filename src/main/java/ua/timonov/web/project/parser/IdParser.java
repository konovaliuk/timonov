package ua.timonov.web.project.parser;

import org.apache.log4j.Logger;
import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.util.ExceptionMessages;

public class IdParser implements Parser<Long> {

    private static final Logger LOGGER = Logger.getLogger(IdParser.class);

    @Override
    public Long parse(String string, String field) throws ParsingException {
        boolean success = true;
        Long id = 0L;
        try {
            id = Long.valueOf(string);
        } catch (NumberFormatException e) {
            success = false;
        }
        if (!success || id == null || id < 0) {
            String message = ExceptionMessages.getMessage(ExceptionMessages.WRONG_VALUE) + string +
                    ExceptionMessages.getMessage(ExceptionMessages.IN_FIELD) + field;
            LOGGER.error(message);
            throw new ParsingException(message);
        }
        return id;
    }

    @Override
    public String createString(Long value) {
        return null;
    }
}
