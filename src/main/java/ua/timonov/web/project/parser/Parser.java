package ua.timonov.web.project.parser;

import ua.timonov.web.project.exception.ParsingException;

/**
 * Parser from String to abstract data type
 * @param <T>           type of data
 */
public interface Parser<T> {

    /**
     * parses string to abstract data type
     * @param value     string with value to be parsed
     * @param field     name of input field with value
     * @return          parsed data
     * @throws ParsingException     if error occurs while parsing
     */
    T parse(String value, String field) throws ParsingException;
}
