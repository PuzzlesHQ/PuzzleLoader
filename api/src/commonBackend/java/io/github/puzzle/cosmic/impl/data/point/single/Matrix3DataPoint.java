package io.github.puzzle.cosmic.impl.data.point.single;

import com.badlogic.gdx.math.Matrix3;
import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import io.github.puzzle.cosmic.impl.data.point.AbstractDataPoint;

public class Matrix3DataPoint extends AbstractDataPoint<Matrix3> {

    public Matrix3DataPoint() {
        super(Matrix3.class);
    }

    public Matrix3DataPoint(Matrix3 value) {
        super(Matrix3.class, value);
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        setValue(new Matrix3(
                deserializer.readFloatArray("v")
        ));
    }

    @Override
    public void write(CRBinSerializer serializer) {
        serializer.writeFloatArray("v", value.val);
    }
}
