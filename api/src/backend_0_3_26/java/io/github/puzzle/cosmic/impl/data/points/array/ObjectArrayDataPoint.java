package io.github.puzzle.cosmic.impl.data.points.array;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import finalforeach.cosmicreach.savelib.crbin.ICRBinSerializable;
import io.github.puzzle.cosmic.impl.data.points.AbstractDataPoint;

public class ObjectArrayDataPoint extends AbstractDataPoint<ICRBinSerializable[]> {

    public ObjectArrayDataPoint() {
        super(ICRBinSerializable[].class);
    }

    public ObjectArrayDataPoint(ICRBinSerializable[] value) {
        super(ICRBinSerializable[].class, value);
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        try {
            int size = deserializer.readInt("size", 0);
            setValue(new ICRBinSerializable[size]);
            for (int i = 0; i < size; i++) {
                value[i] = (ICRBinSerializable) deserializer.readObj(
                        "v_" + i,
                        Class.forName(deserializer.readString("type_" + i))
                );
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
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
}
