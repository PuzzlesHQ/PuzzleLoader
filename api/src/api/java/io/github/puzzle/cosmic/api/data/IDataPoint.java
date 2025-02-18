package io.github.puzzle.cosmic.api.data;

import io.github.puzzle.cosmic.util.ApiDeclaration;

public interface IDataPoint<T> {

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
