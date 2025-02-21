package io.github.puzzle.cosmic.impl.data.point.single;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import finalforeach.cosmicreach.util.Identifier;
import io.github.puzzle.cosmic.api.util.IPuzzleIdentifier;
import io.github.puzzle.cosmic.impl.data.point.AbstractDataPoint;

public class PuzzleIdentifierDataPoint extends AbstractDataPoint<IPuzzleIdentifier> {

    public PuzzleIdentifierDataPoint() {
        super(IPuzzleIdentifier.class);
    }

    public PuzzleIdentifierDataPoint(IPuzzleIdentifier value) {
        super(IPuzzleIdentifier.class, value);
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        setValue(IPuzzleIdentifier.as(Identifier.of(deserializer.readString("v"))));
    }

    @Override
    public void write(CRBinSerializer serializer) {
        serializer.writeString("v", value.asString());
    }
}
