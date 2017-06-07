package ua.timonov.web.project.parser;

import org.apache.log4j.Logger;
import ua.timonov.web.project.exception.ParsingException;
import ua.timonov.web.project.util.ExceptionMessages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateParser implements Parser<Date> {

    private static final Logger LOGGER = Logger.getLogger(DateParser.class);
    private static final SimpleDateFormat simpleFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public Date parse(String string, String field) throws ParsingException {
        try {
            return simpleFormat.parse(string);
        } catch (ParseException e) {
            String message = ExceptionMessages.getMessage(ExceptionMessages.WRONG_VALUE) + string +
                    ExceptionMessages.getMessage(ExceptionMessages.IN_FIELD) + " " + field;
            LOGGER.error(message);
            throw new ParsingException(message);
        }
    }

    @Override
    public String createString(Date value) {
        return simpleFormat.format(value);
    }
}
