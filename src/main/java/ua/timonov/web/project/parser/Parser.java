package ua.timonov.web.project.parser;

public interface Parser<T> {
    T parse(String string, String field) throws ParsingException;

    String createString(T value);
}
