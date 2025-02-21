package io.github.puzzle.cosmic.impl.data.point;

import io.github.puzzle.cosmic.api.data.point.IDataPoint;
import io.github.puzzle.cosmic.api.data.point.ITaggedDataPoint;
import io.github.puzzle.cosmic.api.data.point.ITaggedDataPointSpec;

import java.util.function.Supplier;

public record TaggedDataPointSpec<V> (
        String name,
        Supplier<IDataPoint<V>> pointSupplier
) implements ITaggedDataPointSpec<V> {

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Supplier<IDataPoint<V>> getValueSupplier() {
        return pointSupplier;
    }

    @Override
    public ITaggedDataPoint<V> create(V v) {
        Supplier<IDataPoint<V>> supplier = getValueSupplier();
        IDataPoint<V> point = supplier.get();
        point.setValue(v);

        return (ITaggedDataPoint) new TaggedDataPoint<>(
                getName(),
                point
        );
    }

}
