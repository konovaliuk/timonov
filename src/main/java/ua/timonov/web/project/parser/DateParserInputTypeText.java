package ua.timonov.web.project.parser;

import java.text.SimpleDateFormat;

/**
 * Date parser from input from with type "text"
 */
public class DateParserInputTypeText extends DateParser {

    public DateParserInputTypeText() {
        super(new SimpleDateFormat("dd-MM-yyyy"));
    }
}
