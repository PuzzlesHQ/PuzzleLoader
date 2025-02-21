package io.github.puzzle.cosmic.api.constants;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import io.github.puzzle.cosmic.util.annotation.compile.Alternative;
import io.github.puzzle.cosmic.util.annotation.compile.SourceOnly;

/**
 *
 * @author Mr_Zombii
 * @since 0.3.26
 */
@SourceOnly
@Alternative("Direction")
public enum Direction {
    NEG_X(-1, 0, 0),
    POS_X(1, 0, 0),
    NEG_Y(0, -1, 0),
    POS_Y(0, 1, 0),
    NEG_Z(0, 0, -1),
    POS_Z(0, 0, 1);

    public static final Direction[] ALL_DIRECTIONS = values();
    public static final Direction[] ALL_POS_AXIS = new Direction[]{POS_X, POS_Y, POS_Z};
    public static final Direction[] VERT_AXIS = new Direction[]{NEG_Y, POS_Y};
    private int xOff;
    private int yOff;
    private int zOff;
    private Direction[] allExceptThis;
    private Direction[] allExceptOpposite;
    private Direction[] justThis = new Direction[]{this};
    private Direction oppositeDirection;

    public Direction[] asArray() {
        return this.justThis;
    }

    public Direction[] allOtherDirections() {
        if (this.allExceptThis == null) {
            Array<Direction> a = new Array(Direction.class);
            a.addAll(ALL_DIRECTIONS);
            a.removeValue(this, true);
            this.allExceptThis = (Direction[])a.toArray();
        }

        return this.allExceptThis;
    }

    public Direction[] allDirectionsButOpposite() {
        if (this.allExceptOpposite == null) {
            Array<Direction> a = new Array(Direction.class);
            a.addAll(ALL_DIRECTIONS);
            a.removeValue(this.getOpposite(), true);
            this.allExceptOpposite = (Direction[])a.toArray();
        }

        return this.allExceptOpposite;
    }

    Direction getOpposite() {
        if (this.oppositeDirection == null) {
            Direction var10001;
            switch (this.ordinal()) {
                case 0 -> var10001 = POS_X;
                case 1 -> var10001 = NEG_X;
                case 2 -> var10001 = POS_Y;
                case 3 -> var10001 = NEG_Y;
                case 4 -> var10001 = POS_Z;
                case 5 -> var10001 = NEG_Z;
                default -> throw new IllegalArgumentException("Unexpected value: " + String.valueOf(this));
            }

            this.oppositeDirection = var10001;
        }

        return this.oppositeDirection;
    }

    private Direction(int xOff, int yOff, int zOff) {
        this.xOff = xOff;
        this.yOff = yOff;
        this.zOff = zOff;
    }

    public int getXOffset() {
        return this.xOff;
    }

    public int getYOffset() {
        return this.yOff;
    }

    public int getZOffset() {
        return this.zOff;
    }

    public boolean isXAxis() {
        return this.xOff != 0;
    }

    public boolean isYAxis() {
        return this.yOff != 0;
    }

    public boolean isZAxis() {
        return this.zOff != 0;
    }

    public static Direction randomDirection() {
        return ALL_DIRECTIONS[MathUtils.random(0, ALL_DIRECTIONS.length - 1)];
    }
}
