package io.github.puzzle.cosmic.impl.data.points.single;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import io.github.puzzle.cosmic.impl.data.points.AbstractDataPoint;

public class FloatDataPoint extends AbstractDataPoint<Float> {

    public FloatDataPoint() {
        super(Float.TYPE);
    }

    public FloatDataPoint(Float value) {
        super(Float.TYPE, value);
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        setValue(deserializer.readFloat("v", 0));
    }

    @Override
    public void write(CRBinSerializer serializer) {
        serializer.writeFloat("v", value);
    }
}
