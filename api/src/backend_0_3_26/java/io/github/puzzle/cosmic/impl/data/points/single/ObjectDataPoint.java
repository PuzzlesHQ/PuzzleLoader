package io.github.puzzle.cosmic.impl.data.points.single;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import finalforeach.cosmicreach.savelib.crbin.ICRBinSerializable;
import io.github.puzzle.cosmic.impl.data.points.AbstractDataPoint;

public class ObjectDataPoint extends AbstractDataPoint<ICRBinSerializable> {

    public ObjectDataPoint() {
        super(ICRBinSerializable.class);
    }

    public ObjectDataPoint(ICRBinSerializable value) {
        super(ICRBinSerializable.class, value);
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        try {
            setValue((ICRBinSerializable) deserializer.readObj(
                    "v",
                    Class.forName(deserializer.readString("type"))
            ));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(CRBinSerializer serializer) {
        serializer.writeString("type", value.getClass().getName());
        serializer.writeObj("v", value);
    }
}
