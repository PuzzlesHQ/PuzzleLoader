package io.github.puzzle.cosmic.api.data.point;

import java.util.function.Supplier;

/**
 *
 * @author Mr_Zombii
 * @since 0.3.26
 */
public interface ITaggedDataPointSpec<V> {

    String getName();
    Supplier<IDataPoint<V>> getValueSupplier();

    ITaggedDataPoint<V> create(V value);

}
