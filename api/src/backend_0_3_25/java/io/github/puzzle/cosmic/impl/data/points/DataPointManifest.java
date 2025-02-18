package io.github.puzzle.cosmic.impl.data.points;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import finalforeach.cosmicreach.savelib.crbin.ICRBinSerializable;
import io.github.puzzle.cosmic.api.data.IDataPointManifest;
import io.github.puzzle.cosmic.api.data.IDataPoint;

import java.util.HashMap;
import java.util.Map;

public class DataPointManifest implements IDataPointManifest, ICRBinSerializable {

    Map<String, IDataPoint<?>> dataPointMap = new HashMap<>();

    @Override
    public <T> IDataPoint<T> put(String s, IDataPoint<T> iDataPoint) {
        dataPointMap.put(s, iDataPoint);
        return iDataPoint;
    }

    @Override
    public <T> IDataPoint<T> get(String s, Class<T> aClass) {
        return dataPointMap.get(s).cast(aClass);
    }

    @Override
    public IDataPoint<?> get(String s) {
        return dataPointMap.get(s);
    }

    @Override
    public boolean has(String s) {
        return dataPointMap.containsKey(s);
    }

    @Override
    public boolean has(String s, Class<?> aClass) {
        return has(s) && dataPointMap.get(s).isOfType(aClass);
    }

    @Override
    public void read(CRBinDeserializer crBinDeserializer) {
        String[] keys = crBinDeserializer.readStringArray("keys");
        for (String key : keys) {
            try {
                Class<? extends AbstractDataPoint> pointType = (Class<? extends AbstractDataPoint>) Class.forName(crBinDeserializer.readString("point_type_" + key));
                dataPointMap.put(key, crBinDeserializer.readObj("point_" + key, pointType));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void write(CRBinSerializer crBinSerializer) {
        crBinSerializer.writeStringArray("keys", dataPointMap.keySet().toArray(new String[0]));
        for (String key : dataPointMap.keySet()) {
            IDataPoint<?> value = dataPointMap.get(key);

            if (value instanceof AbstractDataPoint<?> dataPoint) {
                crBinSerializer.writeString("point_type_" + key, value.getClass().getName());
                crBinSerializer.writeObj("point_" + key, dataPoint);
            } else {
                System.err.println("Could not write datapoint at key \"" + key + "\" because it did not extend class \"" + AbstractDataPoint.class + "\"");
            }
        }
    }

}
