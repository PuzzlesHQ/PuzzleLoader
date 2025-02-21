package io.github.puzzle.cosmic.impl.data.point.array;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import finalforeach.cosmicreach.savelib.crbin.SchemaType;
import io.github.puzzle.cosmic.impl.data.point.AbstractDataPoint;

public class ShortObjectArrayDataPoint extends AbstractDataPoint<Short[]> {

    public ShortObjectArrayDataPoint() {
        super(Short[].class);
    }

    public ShortObjectArrayDataPoint(Short[] value) {
        super(Short[].class, value);
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        short[] backing = deserializer.readShortArray("v");
        setValue(new Short[backing.length]);
        for (int i = 0; i < backing.length; i++)
            value[i] = backing[i];
    }

    @Override
    public void write(CRBinSerializer serializer) {
        serializer.writeArray("v", SchemaType.SHORT_ARRAY, value.length, (i) -> {
            serializer.writeShort(null, value[i]);
        });
    }
}
