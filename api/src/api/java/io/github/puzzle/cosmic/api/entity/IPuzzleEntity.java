package io.github.puzzle.cosmic.api.entity;

import com.badlogic.gdx.math.Vector3;
import io.github.puzzle.cosmic.api.data.point.IDataPointManifest;
import io.github.puzzle.cosmic.api.util.IPuzzleIdentifier;
import io.github.puzzle.cosmic.util.ApiDeclaration;

@ApiDeclaration(api = IPuzzleEntity.class, impl = "Entity")
public interface IPuzzleEntity {

    Vector3 _getPosition();
    Vector3 _getViewDirection();

    IPuzzleEntityUniqueId _getUniqueId();
    IPuzzleIdentifier _getEntityId();

    boolean _isDead();

    IDataPointManifest _getPointManifest();
}
