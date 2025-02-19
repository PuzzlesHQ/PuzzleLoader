package io.github.puzzle.cosmic.impl.data.point.single;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import io.github.puzzle.cosmic.impl.data.point.AbstractDataPoint;

public class LongDataPoint extends AbstractDataPoint<Long> {

    public LongDataPoint() {
        super(Long.TYPE);
    }

    public LongDataPoint(Long value) {
        super(Long.TYPE, value);
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        setValue(deserializer.readLong("v", 0));
    }

    @Override
    public void write(CRBinSerializer serializer) {
        serializer.writeLong("v", value);
    }
}
