package io.github.puzzle.cosmic.impl.data.point.array;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import finalforeach.cosmicreach.savelib.crbin.SchemaType;
import io.github.puzzle.cosmic.impl.data.point.AbstractDataPoint;

public class LongObjectArrayDataPoint extends AbstractDataPoint<Long[]> {

    public LongObjectArrayDataPoint() {
        super(Long[].class);
    }

    public LongObjectArrayDataPoint(Long[] value) {
        super(Long[].class, value);
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        long[] backing = deserializer.readLongArray("v");
        setValue(new Long[backing.length]);
        for (int i = 0; i < backing.length; i++)
            value[i] = backing[i];
    }

    @Override
    public void write(CRBinSerializer serializer) {
        serializer.writeArray("v", SchemaType.LONG_ARRAY, value.length, (i) -> {
            serializer.writeLong(null, value[i]);
        });
    }
}
