package io.github.puzzle.cosmic.impl.data.point.array;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import finalforeach.cosmicreach.savelib.crbin.SchemaType;
import io.github.puzzle.cosmic.impl.data.point.AbstractDataPoint;

public class DoubleObjectArrayDataPoint extends AbstractDataPoint<Double[]> {

    public DoubleObjectArrayDataPoint() {
        super(Double[].class);
    }

    public DoubleObjectArrayDataPoint(Double[] value) {
        super(Double[].class, value);
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        double[] backing = deserializer.readDoubleArray("v");
        setValue(new Double[backing.length]);
        for (int i = 0; i < backing.length; i++)
            value[i] = backing[i];
    }

    @Override
    public void write(CRBinSerializer serializer) {
        serializer.writeArray("v", SchemaType.DOUBLE_ARRAY, value.length, (i) -> {
            serializer.writeDouble(null, value[i]);
        });
    }
}
