package ua.timonov.web.project.parser;

import java.util.Date;

public class FactoryParser {
    private static final FactoryParser instance = new FactoryParser();
    private static final DateParser dateParser = new DateParser();

    private FactoryParser() {
    }

    public static FactoryParser getInstance() {
        return instance;
    }

    public static Parser<Date> createDateParser() {
        return dateParser;
    }
}
