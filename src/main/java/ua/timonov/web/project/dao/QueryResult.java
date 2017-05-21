package ua.timonov.web.project.dao;

import java.util.List;

@Deprecated
public class QueryResult<T> {
    private List<T> result;
    private int totalRowsCount;

    public QueryResult(List<T> result, int totalRowsCount) {
        this.result = result;
        this.totalRowsCount = totalRowsCount;
    }

    public List<T> getResult() {
        return result;
    }

    public int getTotalRowsCount() {
        return totalRowsCount;
    }
}