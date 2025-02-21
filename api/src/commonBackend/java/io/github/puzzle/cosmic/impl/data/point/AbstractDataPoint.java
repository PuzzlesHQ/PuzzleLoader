package io.github.puzzle.cosmic.impl.data.point;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import finalforeach.cosmicreach.savelib.crbin.ICRBinSerializable;
import io.github.puzzle.cosmic.api.data.point.IDataPoint;

public abstract class AbstractDataPoint<T> implements IDataPoint<T>, ICRBinSerializable {

    protected T value;
    protected final Class<T> type;

    public AbstractDataPoint(Class<T> type) {
        this.type = type;
    }

    public AbstractDataPoint(Class<T> type, T value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public T setValue(T t) {
        return value = t;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public Class<T> getClassType() {
        return type;
    }

    @Override
    public abstract void read(CRBinDeserializer deserializer);

    @Override
    public abstract void write(CRBinSerializer serializer);

}
