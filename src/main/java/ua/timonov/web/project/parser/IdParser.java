package ua.timonov.web.project.parser;

import ua.timonov.web.project.exception.ParsingException;

public class IdParser implements Parser<Long> {
    @Override
    public Long parse(String string, String field) throws ParsingException {
        // TODO
        return new Long("7");
    }

    @Override
    public String createString(Long value) {
        return null;
    }
}
