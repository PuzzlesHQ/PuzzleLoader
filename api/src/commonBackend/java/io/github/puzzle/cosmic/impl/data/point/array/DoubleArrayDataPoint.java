package io.github.puzzle.cosmic.impl.data.point.array;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import io.github.puzzle.cosmic.impl.data.point.AbstractDataPoint;

public class DoubleArrayDataPoint extends AbstractDataPoint<double[]> {

    public DoubleArrayDataPoint() {
        super(double[].class);
    }

    public DoubleArrayDataPoint(double[] value) {
        super(double[].class, value);
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        setValue(deserializer.readDoubleArray("v"));
    }

    @Override
    public void write(CRBinSerializer serializer) {
        serializer.writeDoubleArray("v", value);
    }
}
