package io.github.puzzle.cosmic.impl.data.point.array;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import finalforeach.cosmicreach.savelib.crbin.SchemaType;
import io.github.puzzle.cosmic.impl.data.point.AbstractDataPoint;

public class IntObjectArrayDataPoint extends AbstractDataPoint<Integer[]> {

    public IntObjectArrayDataPoint() {
        super(Integer[].class);
    }

    public IntObjectArrayDataPoint(Integer[] value) {
        super(Integer[].class, value);
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        int[] backing = deserializer.readIntArray("v");
        setValue(new Integer[backing.length]);
        for (int i = 0; i < backing.length; i++)
            value[i] = backing[i];
    }

    @Override
    public void write(CRBinSerializer serializer) {
        serializer.writeArray("v", SchemaType.INT_ARRAY, value.length, (i) -> {
            serializer.writeInt(null, value[i]);
        });
    }
}
