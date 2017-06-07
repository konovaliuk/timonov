package ua.timonov.web.project.parser;

import java.text.SimpleDateFormat;

public class DateParserInputTypeText extends DateParser {

    public DateParserInputTypeText() {
        super(new SimpleDateFormat("dd-MM-yyyy"));
    }
}
