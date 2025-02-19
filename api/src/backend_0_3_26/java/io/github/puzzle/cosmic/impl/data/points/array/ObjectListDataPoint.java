package io.github.puzzle.cosmic.impl.data.points.array;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import finalforeach.cosmicreach.savelib.crbin.ICRBinSerializable;
import io.github.puzzle.cosmic.impl.data.points.AbstractDataPoint;
import io.github.puzzle.cosmic.impl.data.points.DataPointManifest;
import io.github.puzzle.cosmic.impl.data.points.single.BooleanDataPoint;
import io.github.puzzle.cosmic.impl.data.points.single.ManifestDataPoint;
import io.github.puzzle.cosmic.impl.data.points.single.StringDataPoint;

import java.util.ArrayList;
import java.util.List;

public class ObjectListDataPoint extends AbstractDataPoint<List> {

    public ObjectListDataPoint() {
        super(List.class);

        value = new ArrayList<>();
    }

    public ObjectListDataPoint(List<ICRBinSerializable> value) {
        super(List.class, value);
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        try {
            int size = deserializer.readInt("size", 0);
            setValue(new ArrayList<>());
            for (int i = 0; i < size; i++) {
                value.add(deserializer.readObj(
                        "v_" + i,
                        Class.forName(deserializer.readString("type_" + i))
                ));
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ICRBinSerializable> setValue(List<ICRBinSerializable> list) {
        return super.setValue(list);
    }

    @Override
    public void write(CRBinSerializer serializer) {
        serializer.writeInt("size", value.size());
        for (int i = 0; i < value.size(); i++) {
            serializer.writeString("type_" + i, value.get(i).getClass().getName());
            serializer.writeObj("v_" + i, (ICRBinSerializable) value.get(i));
        }
    }
}
