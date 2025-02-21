package io.github.puzzle.cosmic.impl.data.point.single;

import com.badlogic.gdx.math.Matrix4;
import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import io.github.puzzle.cosmic.impl.data.point.AbstractDataPoint;

public class Matrix4DataPoint extends AbstractDataPoint<Matrix4> {

    public Matrix4DataPoint() {
        super(Matrix4.class);
    }

    public Matrix4DataPoint(Matrix4 value) {
        super(Matrix4.class, value);
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        setValue(new Matrix4(
                deserializer.readFloatArray("v")
        ));
    }

    @Override
    public void write(CRBinSerializer serializer) {
        serializer.writeFloatArray("v", value.val);
    }
}
