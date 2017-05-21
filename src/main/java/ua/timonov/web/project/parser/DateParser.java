package ua.timonov.web.project.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateParser implements Parser<Date> {
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Date parse(String string, String field) throws ParsingException {
        try {
            return simpleDateFormat.parse(string);
        } catch (ParseException e) {
            throw new ParsingException("Wrong value \"" + string + "\" in field " + field);
        }
    }

    @Override
    public String createString(Date value) {
        return simpleDateFormat.format(value);
    }
}
