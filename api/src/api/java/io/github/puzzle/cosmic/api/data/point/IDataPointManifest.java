package io.github.puzzle.cosmic.api.data.point;

/**
 *
 * @author Mr_Zombii
 * @since 0.3.26
 */
public interface IDataPointManifest {

    <T> ITaggedDataPoint<T> put(ITaggedDataPoint<T> point);
    <T> IDataPoint<T> put(String name, IDataPoint<T> point);
    <T> IDataPoint<T> get(String name, Class<T> type);
    IDataPoint<?> get(String name);

    boolean has(String name);
    boolean has(String name, Class<?> type);

    void remove(String name);

}
