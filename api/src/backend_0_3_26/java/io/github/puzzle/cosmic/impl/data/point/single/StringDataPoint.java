package io.github.puzzle.cosmic.impl.data.point.single;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import io.github.puzzle.cosmic.impl.data.point.AbstractDataPoint;

public class StringDataPoint extends AbstractDataPoint<String> {

    public StringDataPoint() {
        super(String.class);
    }

    public StringDataPoint(String value) {
        super(String.class, value);
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        setValue(deserializer.readString("v"));
    }

    @Override
    public void write(CRBinSerializer serializer) {
        serializer.writeString("v", value);
    }
}
