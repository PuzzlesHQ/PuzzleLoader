package io.github.puzzle.cosmic.impl.data.point.array;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import finalforeach.cosmicreach.savelib.crbin.ICRBinSerializable;
import io.github.puzzle.cosmic.impl.data.point.AbstractDataPoint;
import org.jetbrains.annotations.Nullable;

public class ObjectArrayDataPoint<T extends ICRBinSerializable> extends AbstractDataPoint<T[]> {

    public ObjectArrayDataPoint() {
        super(null);
    }

    public ObjectArrayDataPoint(T[] value) {
        super(null, value);
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        try {
            int size = deserializer.readInt("size", 0);
            setValue((T[]) new ICRBinSerializable[size]);
            for (int i = 0; i < size; i++) {
                value[i] = (T) deserializer.readObj(
                        "v_" + i,
                        Class.forName(deserializer.readString("type_" + i))
                );
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isOfType(Class<?> typeClass) {
        if (value == null) {
            return ICRBinSerializable[].class.isAssignableFrom(typeClass);
        } else {
            return value.getClass().isAssignableFrom(typeClass);
        }
    }

    @Override
    public void write(CRBinSerializer serializer) {
        serializer.writeInt("size", value.length);
        for (int i = 0; i < value.length; i++) {
            serializer.writeString("type_" + i, value[i].getClass().getName());
            serializer.writeObj("v_" + i, value[i]);
        }
    }

    @Override
    public @Nullable Class<T[]> getClassType() {
        return super.getClassType();
    }
}
