package io.github.puzzle.cosmic.impl.data.point.single;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import finalforeach.cosmicreach.savelib.crbin.ICRBinSerializable;
import io.github.puzzle.cosmic.impl.data.point.AbstractDataPoint;
import org.jetbrains.annotations.Nullable;

public class ObjectDataPoint<T extends ICRBinSerializable> extends AbstractDataPoint<T> {

    public ObjectDataPoint() {
        super(null);
    }

    public ObjectDataPoint(T value) {
        super(null, value);
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        try {
            setValue((T) deserializer.readObj(
                    "v",
                    Class.forName(deserializer.readString("type"))
            ));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isOfType(Class<?> typeClass) {
        if (value == null) {
            return ICRBinSerializable.class.isAssignableFrom(typeClass);
        } else {
            return value.getClass().isAssignableFrom(typeClass);
        }
    }

    @Override
    public @Nullable Class<T> getClassType() {
        return super.getClassType();
    }

    @Override
    public void write(CRBinSerializer serializer) {
        serializer.writeString("type", value.getClass().getName());
        serializer.writeObj("v", value);
    }
}
