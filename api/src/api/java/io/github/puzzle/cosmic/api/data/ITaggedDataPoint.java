package io.github.puzzle.cosmic.api.data;

public interface ITaggedDataPoint<T> extends IDataPoint<T> {

    void setName(String s);
    String getName();

}
