package io.github.puzzle.cosmic.impl.data.point.array;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import finalforeach.cosmicreach.savelib.crbin.ICRBinSerializable;
import io.github.puzzle.cosmic.impl.data.point.AbstractDataPoint;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ObjectListDataPoint<T extends ICRBinSerializable> extends AbstractDataPoint<List<T>> {

    public ObjectListDataPoint() {
        super(null);

        value = new ArrayList<>();
    }

    public ObjectListDataPoint(List<T> value) {
        super(null, value);
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        try {
            int size = deserializer.readInt("size", 0);
            setValue(new ArrayList<>());
            for (int i = 0; i < size; i++) {
                value.add((T) deserializer.readObj(
                        "v_" + i,
                        Class.forName(deserializer.readString("type_" + i))
                ));
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
        serializer.writeInt("size", value.size());
        for (int i = 0; i < value.size(); i++) {
            serializer.writeString("type_" + i, value.get(i).getClass().getName());
            serializer.writeObj("v_" + i, (ICRBinSerializable) value.get(i));
        }
    }

    @Override
    public @Nullable Class<List<T>> getClassType() {
        return super.getClassType();
    }
}
