package io.github.puzzle.cosmic.impl.data.point.single;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import io.github.puzzle.cosmic.impl.data.point.AbstractDataPoint;

public class ShortDataPoint extends AbstractDataPoint<Short> {

    public ShortDataPoint() {
        super(Short.TYPE);
    }

    public ShortDataPoint(Short value) {
        super(Short.TYPE, value);
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        setValue(deserializer.readShort("v", (short) 0));
    }

    @Override
    public void write(CRBinSerializer serializer) {
        serializer.writeShort("v", value);
    }
}
