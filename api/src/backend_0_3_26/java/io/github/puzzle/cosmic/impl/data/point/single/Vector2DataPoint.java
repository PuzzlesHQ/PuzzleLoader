package io.github.puzzle.cosmic.impl.data.point.single;

import com.badlogic.gdx.math.Vector2;
import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import io.github.puzzle.cosmic.impl.data.point.AbstractDataPoint;

public class Vector2DataPoint extends AbstractDataPoint<Vector2> {

    public Vector2DataPoint() {
        super(Vector2.class);
    }

    public Vector2DataPoint(Vector2 value) {
        super(Vector2.class, value);
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        setValue(new Vector2(
                deserializer.readFloat("x", 0),
                deserializer.readFloat("y", 0)
        ));
    }

    @Override
    public void write(CRBinSerializer serializer) {
        serializer.writeFloat("x", value.x);
        serializer.writeFloat("y", value.y);
    }
}
