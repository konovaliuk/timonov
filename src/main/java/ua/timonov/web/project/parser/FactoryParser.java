package ua.timonov.web.project.parser;

import java.util.Date;

public class FactoryParser {
    private static final FactoryParser instance = new FactoryParser();
    private static final DateParserInputTypeText dateParserInputTypeText = new DateParserInputTypeText();
    private static final DateParserInputTypeDate dateParserInputTypeDate = new DateParserInputTypeDate();
    private static final IdParser IdParser = new IdParser();

    private FactoryParser() {
    }

    public static FactoryParser getInstance() {
        return instance;
    }

    public static Parser<Long> createIdParser() {
        return IdParser;
    }

    public static Parser<Date> createDateParserInputTypeText() {
        return dateParserInputTypeText;
    }

    public static Parser<Date> createDateParserInputTypeDate() {
        return dateParserInputTypeDate;
    }
}
