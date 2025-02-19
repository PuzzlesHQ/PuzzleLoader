package io.github.puzzle.cosmic.impl.data.point.array;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import finalforeach.cosmicreach.savelib.crbin.SchemaType;
import io.github.puzzle.cosmic.impl.data.point.AbstractDataPoint;

public class ByteObjectArrayDataPoint extends AbstractDataPoint<Byte[]> {

    public ByteObjectArrayDataPoint() {
        super(Byte[].class);
    }

    public ByteObjectArrayDataPoint(Byte[] value) {
        super(Byte[].class, value);
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        byte[] backing = deserializer.readByteArray("v");
        setValue(new Byte[backing.length]);
        for (int i = 0; i < backing.length; i++)
            value[i] = backing[i];
    }

    @Override
    public void write(CRBinSerializer serializer) {
        serializer.writeArray("v", SchemaType.BYTE_ARRAY, value.length, (i) -> {
            serializer.writeByte(null, value[i]);
        });
    }
}
