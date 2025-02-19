package io.github.puzzle.cosmic.impl.data.points.array;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import io.github.puzzle.cosmic.impl.data.points.AbstractDataPoint;

public class ByteArrayDataPoint extends AbstractDataPoint<byte[]> {

    public ByteArrayDataPoint() {
        super(byte[].class);
    }

    public ByteArrayDataPoint(byte[] value) {
        super(byte[].class, value);
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        setValue(deserializer.readByteArray("v"));
    }

    @Override
    public void write(CRBinSerializer serializer) {
        serializer.writeByteArray("v", value);
    }
}
