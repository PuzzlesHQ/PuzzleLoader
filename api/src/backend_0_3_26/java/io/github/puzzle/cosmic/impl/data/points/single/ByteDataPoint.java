package io.github.puzzle.cosmic.impl.data.points.single;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import io.github.puzzle.cosmic.impl.data.points.AbstractDataPoint;

public class ByteDataPoint extends AbstractDataPoint<Byte> {

    public ByteDataPoint() {
        super(Byte.TYPE);
    }

    public ByteDataPoint(Byte value) {
        super(Byte.TYPE, value);
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        setValue((byte) deserializer.readInt("v", 0));
    }

    @Override
    public void write(CRBinSerializer serializer) {
        serializer.writeByte("v", value);
    }
}
