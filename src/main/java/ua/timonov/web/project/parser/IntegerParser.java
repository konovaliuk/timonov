package ua.timonov.web.project.parser;

import org.apache.log4j.Logger;
import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.util.ExceptionMessages;

/**
 * Parser for Integer values
 */
public class IntegerParser implements Parser<Integer> {

    private static final Logger LOGGER = Logger.getLogger(IntegerParser.class);

    @Override
    public Integer parse(String value, String field) throws ParsingException {
        boolean success = true;
        Integer result = 0;
        try {
            result = Integer.valueOf(value);
        } catch (Exception e) {
            success = false;
        }
        if (!success || result == null || result < 0) {
            String message = ExceptionMessages.getMessage(ExceptionMessages.WRONG_VALUE) + value +
                    ExceptionMessages.getMessage(ExceptionMessages.IN_FIELD) + field;
            LOGGER.error(message);
            throw new ParsingException(message);
        }
        return result;
    }
}
