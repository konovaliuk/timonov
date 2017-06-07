package ua.timonov.web.project.parser;

import org.apache.log4j.Logger;
import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.util.ExceptionMessages;

/**
 * Parser for Double values
 */
public class DoubleParser implements Parser<Double> {

    private static final Logger LOGGER = Logger.getLogger(DoubleParser.class);

    @Override
    public Double parse(String value, String field) throws ParsingException {
        boolean success = true;
        Double result = 0d;
        try {
            result = Double.valueOf(value);
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
