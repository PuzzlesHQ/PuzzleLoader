package io.github.puzzle.cosmic.impl.data.point;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import io.github.puzzle.cosmic.api.data.point.IDataPoint;
import io.github.puzzle.cosmic.api.data.point.ITaggedDataPoint;
import org.jetbrains.annotations.Nullable;

public class TaggedDataPoint<V> extends AbstractDataPoint<IDataPoint<V>> implements ITaggedDataPoint<IDataPoint<V>> {

    protected String name;

    public TaggedDataPoint() {
        super(null);
    }

    public TaggedDataPoint(String name) {
        super(null, null);

        this.name = name;
    }

    public TaggedDataPoint(String name, IDataPoint<V> value) {
        super(null, value);

        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        name = deserializer.readString("tag-name");
        try {
            value = (IDataPoint<V>) deserializer.readObj("tag-value", Class.forName(deserializer.readString("tag-type")));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(CRBinSerializer serializer) {
        serializer.writeString("tag-name", name);
        serializer.writeString("tag-type", value.getClass().getName());
        serializer.writeObj("tag-value", value);
    }

    @Override
    public boolean isOfType(Class<?> typeClass) {
        if (value == null) {
            return IDataPoint.class.isAssignableFrom(typeClass);
        } else {
            return value.getClass().isAssignableFrom(typeClass);
        }
    }

    @Override
    public @Nullable Class<IDataPoint<V>> getClassType() {
        return super.getClassType();
    }
}
