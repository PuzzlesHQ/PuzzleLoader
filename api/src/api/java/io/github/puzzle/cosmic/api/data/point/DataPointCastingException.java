package io.github.puzzle.cosmic.api.data.point;

/**
 *
 * @author Mr_Zombii
 * @since 0.3.26
 */
public class DataPointCastingException extends RuntimeException {

    public static DataPointCastingException of(IDataPoint<?> point, Class<?> clazz) {
        return new DataPointCastingException("Cannot cast IDataPoint<" + point.getClassType().getSimpleName() + "> to IDataPoint<" + clazz.getSimpleName() + "> since they are not the same or similar underlying types.");
    }

    public DataPointCastingException(String message) {
        super(message);
    }

}
