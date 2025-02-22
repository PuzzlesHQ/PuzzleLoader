package io.github.puzzle.cosmic.impl.data.point;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import finalforeach.cosmicreach.savelib.crbin.ICRBinSerializable;
import io.github.puzzle.cosmic.api.data.point.ITaggedDataPoint;

public class TaggedObjectDataPoint extends AbstractDataPoint<ICRBinSerializable> implements ITaggedDataPoint<ICRBinSerializable> {

    protected String name;

    public TaggedObjectDataPoint() {
        super(ICRBinSerializable.class);
    }

    public TaggedObjectDataPoint(String name) {
        super(ICRBinSerializable.class, null);

        this.name = name;
    }

    public TaggedObjectDataPoint(String name, ICRBinSerializable value) {
        super(ICRBinSerializable.class, value);

        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        name = deserializer.readString("tag-name");
        try {
            value = (ICRBinSerializable) deserializer.readObj("tag-value", Class.forName(deserializer.readString("tag-type")));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(CRBinSerializer serializer) {
        serializer.writeString("tag-name", name);
        serializer.writeString("tag-type", value.getClass().getName());
        serializer.writeObj("tag-value", value);
    }

}
