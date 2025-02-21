package io.github.puzzle.cosmic.impl.data.point.single;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import io.github.puzzle.cosmic.impl.data.point.AbstractDataPoint;

import java.lang.reflect.Field;

public class DoubleDataPoint extends AbstractDataPoint<Double> {

    public DoubleDataPoint() {
        super(Double.TYPE);
    }

    public DoubleDataPoint(Double value) {
        super(Double.TYPE, value);
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        try {
            Field m = CRBinDeserializer.class.getDeclaredField("doubleValues");
            m.setAccessible(true);
            setValue((Double) m.get(deserializer));
        } catch (NoSuchFieldException | IllegalAccessException ignored) {}
    }

    @Override
    public void write(CRBinSerializer serializer) {
        serializer.writeDouble("v", value);
    }
}
