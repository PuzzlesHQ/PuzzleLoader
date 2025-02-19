package io.github.puzzle.cosmic.impl.data.points.array;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import io.github.puzzle.cosmic.impl.data.points.AbstractDataPoint;

public class ShortArrayDataPoint extends AbstractDataPoint<short[]> {

    public ShortArrayDataPoint() {
        super(short[].class);
    }

    public ShortArrayDataPoint(short[] value) {
        super(short[].class, value);
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        setValue(deserializer.readShortArray("v"));
    }

    @Override
    public void write(CRBinSerializer serializer) {
        serializer.writeShortArray("v", value);
    }
}
