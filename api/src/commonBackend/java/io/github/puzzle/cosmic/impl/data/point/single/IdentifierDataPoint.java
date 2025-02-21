package io.github.puzzle.cosmic.impl.data.point.single;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import finalforeach.cosmicreach.util.Identifier;
import io.github.puzzle.cosmic.impl.data.point.AbstractDataPoint;

public class IdentifierDataPoint extends AbstractDataPoint<Identifier> {

    public IdentifierDataPoint() {
        super(Identifier.class);
    }

    public IdentifierDataPoint(Identifier value) {
        super(Identifier.class, value);
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        setValue(Identifier.of(deserializer.readString("v")));
    }

    @Override
    public void write(CRBinSerializer serializer) {
        serializer.writeString("v", value.getNamespace() + ":" + value.getName());
    }
}
