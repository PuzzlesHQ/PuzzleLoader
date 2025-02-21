package io.github.puzzle.cosmic.impl.mixin.entity;

import com.badlogic.gdx.math.Vector3;
import finalforeach.cosmicreach.blockentities.BlockEntity;
import finalforeach.cosmicreach.entities.Entity;
import finalforeach.cosmicreach.entities.EntityUniqueId;
import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import finalforeach.cosmicreach.util.Identifier;
import io.github.puzzle.cosmic.api.block.IPuzzleBlockEntity;
import io.github.puzzle.cosmic.api.data.point.IDataPointManifest;
import io.github.puzzle.cosmic.api.entity.IPuzzleEntity;
import io.github.puzzle.cosmic.api.entity.IPuzzleEntityUniqueId;
import io.github.puzzle.cosmic.api.util.IPuzzleIdentifier;
import io.github.puzzle.cosmic.impl.data.point.DataPointManifest;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin implements IPuzzleEntity {

    @Unique
    private final transient Entity puzzleLoader$entity = IPuzzleEntity.as(this);

    @Override
    public Vector3 _getPosition() {
        return puzzleLoader$entity.position;
    }

    @Override
    public Vector3 _getViewDirection() {
        return puzzleLoader$entity.viewDirection;
    }

    @Override
    public IPuzzleEntityUniqueId _getUniqueId() {
        return IPuzzleEntityUniqueId.as(puzzleLoader$entity.uniqueId);
    }

    @Override
    public IPuzzleIdentifier _getEntityId() {
        return IPuzzleIdentifier.as(Identifier.of(puzzleLoader$entity.entityTypeId));
    }

    @Override
    public boolean _isDead() {
        return puzzleLoader$entity.isDead();
    }

    @Unique
    private transient DataPointManifest puzzleLoader$manifest = new DataPointManifest();

    @Inject(method = "read", at = @At("TAIL"), remap = false)
    private void write(CRBinDeserializer crbd, CallbackInfo ci) {
        puzzleLoader$manifest = crbd.readObj("data_point_manifest", DataPointManifest.class);
    }

    @Inject(method = "write", at = @At("TAIL"), remap = false)
    private void write(CRBinSerializer crbs, CallbackInfo ci) {
        crbs.writeObj("data_point_manifest", puzzleLoader$manifest);
    }

    @Override
    public IDataPointManifest _getPointManifest() {
        return puzzleLoader$manifest;
    }
}
