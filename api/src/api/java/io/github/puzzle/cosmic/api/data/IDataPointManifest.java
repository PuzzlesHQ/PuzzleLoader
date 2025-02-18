package io.github.puzzle.cosmic.api.data;

public interface IDataPointManifest {

    <T> IDataPoint<T> put(String name, IDataPoint<T> point);
    <T> IDataPoint<T> get(String name, Class<T> type);
    IDataPoint<?> get(String name);

    boolean has(String name);
    boolean has(String name, Class<?> type);

}
