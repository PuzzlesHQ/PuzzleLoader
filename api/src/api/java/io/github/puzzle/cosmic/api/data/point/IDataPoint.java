package io.github.puzzle.cosmic.api.data.point;

import finalforeach.cosmicreach.savelib.crbin.ICRBinSerializable;

public interface IDataPoint<T> extends ICRBinSerializable {

    T setValue(T value);
    T getValue();

    default boolean isOfType(Class<?> typeClass) {
        return getClassType().isAssignableFrom(typeClass);
    }

    default <C> IDataPoint<C> cast(Class<C> castType) {
        if (!isOfType(castType)) throw DataPointCastingException.of(this, castType);

        return (IDataPoint<C>) this;
    }

    Class<T> getClassType();

}
