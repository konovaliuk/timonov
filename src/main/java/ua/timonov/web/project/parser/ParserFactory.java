package ua.timonov.web.project.parser;

import java.util.Date;

/**
 * Factory for data parsers
 */
public class ParserFactory {
    private static final ParserFactory instance = new ParserFactory();
    private static final DateParserInputTypeText dateParserInputTypeText = new DateParserInputTypeText();
    private static final DateParserInputTypeDate dateParserInputTypeDate = new DateParserInputTypeDate();
    private static final IdParser IdParser = new IdParser();
    private static final DoubleParser doubleParser = new DoubleParser();
    private static final IntegerParser integerParser = new IntegerParser();

    private ParserFactory() {
    }

    public static ParserFactory getInstance() {
        return instance;
    }

    public static Parser<Long> createIdParser() {
        return IdParser;
    }

    public static Parser<Integer> createIntegerParser() {
        return integerParser;
    }

    public static Parser<Double> createDoubleParser() {
        return doubleParser;
    }

    public static Parser<Date> createDateParserInputTypeText() {
        return dateParserInputTypeText;
    }

    public static Parser<Date> createDateParserInputTypeDate() {
        return dateParserInputTypeDate;
    }
}
