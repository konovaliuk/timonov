package ua.timonov.web.project.parser;

import ua.timonov.web.project.exception.ParsingException;

public interface Parser<T> {
    T parse(String string, String field) throws ParsingException;

    String createString(T value);
}
