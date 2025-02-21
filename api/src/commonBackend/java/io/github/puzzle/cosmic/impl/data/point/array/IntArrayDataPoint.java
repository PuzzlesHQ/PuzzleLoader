package io.github.puzzle.cosmic.impl.data.point.array;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import io.github.puzzle.cosmic.impl.data.point.AbstractDataPoint;

public class IntArrayDataPoint extends AbstractDataPoint<int[]> {

    public IntArrayDataPoint() {
        super(int[].class);
    }

    public IntArrayDataPoint(int[] value) {
        super(int[].class, value);
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        setValue(deserializer.readIntArray("v"));
    }

    @Override
    public void write(CRBinSerializer serializer) {
        serializer.writeIntArray("v", value);
    }
}
