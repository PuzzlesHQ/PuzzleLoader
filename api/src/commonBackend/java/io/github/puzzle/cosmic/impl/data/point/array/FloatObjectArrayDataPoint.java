package io.github.puzzle.cosmic.impl.data.point.array;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import finalforeach.cosmicreach.savelib.crbin.SchemaType;
import io.github.puzzle.cosmic.impl.data.point.AbstractDataPoint;

public class FloatObjectArrayDataPoint extends AbstractDataPoint<Float[]> {

    public FloatObjectArrayDataPoint() {
        super(Float[].class);
    }

    public FloatObjectArrayDataPoint(Float[] value) {
        super(Float[].class, value);
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        float[] backing = deserializer.readFloatArray("v");
        setValue(new Float[backing.length]);
        for (int i = 0; i < backing.length; i++)
            value[i] = backing[i];
    }

    @Override
    public void write(CRBinSerializer serializer) {
        serializer.writeArray("v", SchemaType.FLOAT_ARRAY, value.length, (i) -> {
            serializer.writeFloat(null, value[i]);
        });
    }
}
