package io.github.puzzle.cosmic.impl.data.point.array;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import io.github.puzzle.cosmic.impl.data.point.AbstractDataPoint;

public class FloatArrayDataPoint extends AbstractDataPoint<float[]> {

    public FloatArrayDataPoint() {
        super(float[].class);
    }

    public FloatArrayDataPoint(float[] value) {
        super(float[].class, value);
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        setValue(deserializer.readFloatArray("v"));
    }

    @Override
    public void write(CRBinSerializer serializer) {
        serializer.writeFloatArray("v", value);
    }
}
