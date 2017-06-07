package ua.timonov.web.project.parser;

import java.text.SimpleDateFormat;

public class DateParserInputTypeDate extends DateParser {

    public DateParserInputTypeDate() {
        super(new SimpleDateFormat("yyyy-MM-dd"));
    }
}
