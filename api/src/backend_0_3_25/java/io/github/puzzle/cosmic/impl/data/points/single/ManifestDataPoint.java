package io.github.puzzle.cosmic.impl.data.points.single;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import io.github.puzzle.cosmic.impl.data.points.AbstractDataPoint;
import io.github.puzzle.cosmic.impl.data.points.DataPointManifest;

public class ManifestDataPoint extends AbstractDataPoint<DataPointManifest> {

    public ManifestDataPoint() {
        super(DataPointManifest.class);
    }

    public ManifestDataPoint(DataPointManifest value) {
        super(DataPointManifest.class, value);
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        setValue(deserializer.readObj("v", DataPointManifest.class));
    }

    @Override
    public void write(CRBinSerializer serializer) {
        serializer.writeObj("v", value);
    }
}
