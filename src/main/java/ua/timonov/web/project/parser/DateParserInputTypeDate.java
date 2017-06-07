package ua.timonov.web.project.parser;

import java.text.SimpleDateFormat;

/**
 * Date parser from input from with type "date"
 */
public class DateParserInputTypeDate extends DateParser {

    public DateParserInputTypeDate() {
        super(new SimpleDateFormat("yyyy-MM-dd"));
    }
}
