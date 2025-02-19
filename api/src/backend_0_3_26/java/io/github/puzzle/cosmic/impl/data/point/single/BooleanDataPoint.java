package io.github.puzzle.cosmic.impl.data.point.single;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import io.github.puzzle.cosmic.impl.data.point.AbstractDataPoint;

public class BooleanDataPoint extends AbstractDataPoint<Boolean> {

    public BooleanDataPoint() {
        super(Boolean.class);
    }

    public BooleanDataPoint(Boolean value) {
        super(Boolean.class, value);
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        setValue(deserializer.readBoolean("v", false));
    }

    @Override
    public void write(CRBinSerializer serializer) {
        serializer.writeBoolean("v", value);
    }
}
