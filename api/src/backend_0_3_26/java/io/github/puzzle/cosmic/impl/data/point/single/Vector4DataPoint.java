package io.github.puzzle.cosmic.impl.data.point.single;

import com.badlogic.gdx.math.Vector4;
import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import io.github.puzzle.cosmic.impl.data.point.AbstractDataPoint;

public class Vector4DataPoint extends AbstractDataPoint<Vector4> {

    public Vector4DataPoint() {
        super(Vector4.class);
    }

    public Vector4DataPoint(Vector4 value) {
        super(Vector4.class, value);
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        setValue(new Vector4(
                deserializer.readFloat("x", 0),
                deserializer.readFloat("y", 0),
                deserializer.readFloat("z", 0),
                deserializer.readFloat("w", 0)
        ));
    }

    @Override
    public void write(CRBinSerializer serializer) {
        serializer.writeFloat("x", value.x);
        serializer.writeFloat("y", value.y);
        serializer.writeFloat("z", value.z);
        serializer.writeFloat("w", value.w);
    }
}
