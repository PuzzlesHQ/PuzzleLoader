package io.github.puzzle.cosmic.impl.data.point;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import finalforeach.cosmicreach.savelib.crbin.ICRBinSerializable;
import io.github.puzzle.cosmic.api.data.point.IDataPoint;
import io.github.puzzle.cosmic.api.data.point.IDataPointManifest;
import io.github.puzzle.cosmic.api.data.point.ITaggedDataPoint;

import java.util.HashMap;
import java.util.Map;

public class DataPointManifest implements IDataPointManifest, ICRBinSerializable {

    Map<String, IDataPoint<?>> dataPointMap = new HashMap<>();

    @Override
    public <T> ITaggedDataPoint<T> put(ITaggedDataPoint<T> iTaggedDataPoint) {
        dataPointMap.put(iTaggedDataPoint.getName(), iTaggedDataPoint);
        return iTaggedDataPoint;
    }

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
    public void remove(String s) {
        dataPointMap.remove(s);
    }

    @Override
    public void read(CRBinDeserializer crBinDeserializer) {
        String[] keys = crBinDeserializer.readStringArray("keys");
        for (String key : keys) {
            try {
                Class<? extends IDataPoint<?>> pointType = (Class<? extends IDataPoint<?>>) Class.forName(crBinDeserializer.readString("point_type_" + key));
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

            crBinSerializer.writeString("point_type_" + key, value.getClass().getName());
            crBinSerializer.writeObj("point_" + key, value);
        }
    }

}
