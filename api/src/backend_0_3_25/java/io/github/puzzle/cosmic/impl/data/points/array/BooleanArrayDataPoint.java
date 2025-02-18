package io.github.puzzle.cosmic.impl.data.points.array;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import io.github.puzzle.cosmic.impl.data.points.AbstractDataPoint;

public class BooleanArrayDataPoint extends AbstractDataPoint<boolean[]> {

    public BooleanArrayDataPoint() {
        super(boolean[].class);
    }

    public BooleanArrayDataPoint(boolean[] value) {
        super(boolean[].class, value);
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        setValue(deserializer.readBooleanArray("v"));
    }

    @Override
    public void write(CRBinSerializer serializer) {
        serializer.writeBooleanArray("v", value);
    }
}
