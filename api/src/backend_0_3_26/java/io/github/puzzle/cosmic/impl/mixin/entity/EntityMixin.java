package io.github.puzzle.cosmic.impl.mixin.entity;

import com.badlogic.gdx.math.Vector3;
import finalforeach.cosmicreach.entities.Entity;
import finalforeach.cosmicreach.entities.EntityUniqueId;
import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import finalforeach.cosmicreach.util.Identifier;
import io.github.puzzle.cosmic.api.data.IDataPointManifest;
import io.github.puzzle.cosmic.api.entity.IPuzzleEntity;
import io.github.puzzle.cosmic.api.entity.IPuzzleEntityUniqueId;
import io.github.puzzle.cosmic.api.util.IPuzzleIdentifier;
import io.github.puzzle.cosmic.impl.data.points.DataPointManifest;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin implements IPuzzleEntity {

    @Shadow public Vector3 position;

    @Shadow public Vector3 viewDirection;

    @Shadow public abstract boolean isDead();

    @Shadow public EntityUniqueId uniqueId;

    @Shadow public String entityTypeId;

    @Override
    public Vector3 _getPosition() {
        return position;
    }

    @Override
    public Vector3 _getViewDirection() {
        return viewDirection;
    }

    @Override
    public IPuzzleEntityUniqueId _getUniqueId() {
        return IPuzzleEntityUniqueId.as(uniqueId);
    }

    @Override
    public IPuzzleIdentifier _getEntityId() {
        return IPuzzleIdentifier.as(Identifier.of(entityTypeId));
    }

    @Override
    public boolean _isDead() {
        return isDead();
    }

    @Unique
    private transient DataPointManifest puzzleLoader$manifest = new DataPointManifest();

    @Inject(method = "read", at = @At("TAIL"))
    private void write(CRBinDeserializer crbd, CallbackInfo ci) {
        puzzleLoader$manifest = crbd.readObj("data_point_manifest", DataPointManifest.class);
    }

    @Inject(method = "write", at = @At("TAIL"))
    private void write(CRBinSerializer crbs, CallbackInfo ci) {
        crbs.writeObj("data_point_manifest", puzzleLoader$manifest);
    }

    @Override
    public IDataPointManifest _getPointManifest() {
        return puzzleLoader$manifest;
    }
}
