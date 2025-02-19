package io.github.puzzle.cosmic.impl.data.points.single;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import io.github.puzzle.cosmic.impl.data.points.AbstractDataPoint;

public class IntegerDataPoint extends AbstractDataPoint<Integer> {

    public IntegerDataPoint() {
        super(Integer.TYPE);
    }

    public IntegerDataPoint(Integer value) {
        super(Integer.TYPE, value);
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        setValue(deserializer.readInt("v", 0));
    }

    @Override
    public void write(CRBinSerializer serializer) {
        serializer.writeInt("v", value);
    }
}
