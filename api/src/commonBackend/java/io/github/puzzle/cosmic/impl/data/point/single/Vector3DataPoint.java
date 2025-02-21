package io.github.puzzle.cosmic.impl.data.point.single;

import com.badlogic.gdx.math.Vector3;
import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import io.github.puzzle.cosmic.impl.data.point.AbstractDataPoint;

public class Vector3DataPoint extends AbstractDataPoint<Vector3> {

    public Vector3DataPoint() {
        super(Vector3.class);
    }

    public Vector3DataPoint(Vector3 value) {
        super(Vector3.class, value);
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        setValue(new Vector3(
                deserializer.readFloat("x", 0),
                deserializer.readFloat("y", 0),
                deserializer.readFloat("z", 0)
        ));
    }

    @Override
    public void write(CRBinSerializer serializer) {
        serializer.writeFloat("x", value.x);
        serializer.writeFloat("y", value.y);
        serializer.writeFloat("z", value.z);
    }
}
