package io.github.puzzle.cosmic.api.data.point;

import java.util.function.Supplier;

public interface ITaggedDataPointSpec<V> {

    String getName();
    Supplier<IDataPoint<V>> getValueSupplier();

    ITaggedDataPoint<V> create(V value);

}
