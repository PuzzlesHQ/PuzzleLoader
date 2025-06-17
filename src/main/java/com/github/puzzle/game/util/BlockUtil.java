package com.github.puzzle.game.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Vector4;
import com.badlogic.gdx.utils.Queue;
import com.github.puzzle.core.loader.meta.Env;
import com.github.puzzle.core.loader.meta.EnvType;
import finalforeach.cosmicreach.GameSingletons;
import finalforeach.cosmicreach.blockentities.BlockEntity;
import finalforeach.cosmicreach.blocks.BlockPosition;
import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.lighting.BlockLightPropagator;
import finalforeach.cosmicreach.networking.packets.blocks.BlockReplacePacket;
import finalforeach.cosmicreach.networking.server.ServerSingletons;
import finalforeach.cosmicreach.util.constants.Direction;
import finalforeach.cosmicreach.world.BlockSetter;
import finalforeach.cosmicreach.world.Chunk;
import finalforeach.cosmicreach.world.Zone;
import finalforeach.cosmicreach.worldgen.ChunkColumn;

public class BlockUtil {

    @Env(EnvType.SERVER)
    public static void setBlockAt(Zone zone, BlockState state, BlockPosition pos) {
        if (pos.chunk() != null){
            BlockSetter.get().replaceBlock(zone, state, pos);
        }
    }

    @Env(EnvType.SERVER)
    public static void setBlockAt(Zone zone, BlockState targetBlockState, BlockEntity be, BlockPosition pos, boolean callBlockEntityOnCreate) {
        if (pos.chunk() != null){
            BlockSetter.get().replaceBlock(zone, targetBlockState, be, pos, callBlockEntityOnCreate);
        }
    }

    static BlockLightPropagator p = new BlockLightPropagator();

    private static void adjustLightsAfterReplace(Zone zone, BlockState oldBlockState, BlockState targetBlockState, BlockPosition blockPos, Queue<BlockPosition> tmpQueue) {
        int oldSkylightAttenuation = 0;
        if (oldBlockState != null) {
            oldSkylightAttenuation = oldBlockState.lightAttenuation;
        }

        int currentSkylight = blockPos.getSkyLight();
        int skylightAttenuation = targetBlockState.lightAttenuation;
        tmpQueue.clear();
        tmpQueue.addFirst(blockPos);
        p.propagateBlockDarkness(zone, tmpQueue);
        tmpQueue.clear();
        tmpQueue.addFirst(blockPos);
        p.propagateBlockLights(zone, tmpQueue);
        boolean propagateShade = currentSkylight > 0 && skylightAttenuation > oldSkylightAttenuation;
        boolean propagateSkylight = currentSkylight != 15 && skylightAttenuation < oldSkylightAttenuation;
        if (propagateShade || propagateSkylight) {
            tmpQueue.clear();
            if (propagateShade) {
                tmpQueue.addFirst(blockPos);
                BlockSetter.get().skylightProp.propagateShade(zone, tmpQueue);
            } else {
                tmpQueue.addFirst(blockPos.getOffsetBlockPos(zone, Direction.POS_X));
                tmpQueue.addFirst(blockPos.getOffsetBlockPos(zone, Direction.POS_Y));
                tmpQueue.addFirst(blockPos.getOffsetBlockPos(zone, Direction.POS_Z));
                tmpQueue.addFirst(blockPos.getOffsetBlockPos(zone, Direction.NEG_X));
                tmpQueue.addFirst(blockPos.getOffsetBlockPos(zone, Direction.NEG_Y));
                tmpQueue.addFirst(blockPos.getOffsetBlockPos(zone, Direction.NEG_Z));
                BlockSetter.get().skylightProp.propagateSkyLights(zone, tmpQueue);
            }
        }

    }

    public static void setBlockAt(Zone zone, BlockState state, Vector3 vector3) {
        setBlockAt(zone, state, (int) vector3.x, (int) vector3.y, (int) vector3.z);
    }

    public static void setBlockAt(Zone zone, BlockState state, int x, int y, int z) {
        setBlockAt(zone, state, getBlockPosAtVec(zone, x, y, z));
    }

    public static void setBlockAt(Zone zone, BlockState state, BlockEntity be, Vector3 vector3, boolean callBlockEntityOnCreate) {
        setBlockAt(zone, state, be, (int) vector3.x, (int) vector3.y, (int) vector3.z, callBlockEntityOnCreate);
    }

    public static void setBlockAt(Zone zone, BlockState state, BlockEntity be, int x, int y, int z, boolean callBlockEntityOnCreate) {
        setBlockAt(zone, state, be, getBlockPosAtVec(zone, x, y, z), callBlockEntityOnCreate);
    }

    public static Chunk getChunkAtVec(Zone zone, int x, int y, int z) {
        return getChunkAtVec(zone, new Vector3(x, y, z));
    }

    public static Chunk getChunkAtVec(Zone zone, Vector3 vector3) {
        int cx = Math.floorDiv((int) vector3.x, 16);
        int cy = Math.floorDiv((int) vector3.y, 16);
        int cz = Math.floorDiv((int) vector3.z, 16);

        Chunk c = zone.getChunkAtChunkCoords(cx, cy, cz);
        if (c == null) {
            c = new Chunk(cx, cy, cz);
            c.initChunkData();
            zone.addChunk(c);
        }

        return c;
    }

    public static BlockPosition getBlockPosAtVec(Zone zone, int x, int y, int z) {
        int cx = Math.floorDiv(x, 16);
        int cy = Math.floorDiv(y, 16);
        int cz = Math.floorDiv(z, 16);

        Chunk c = zone.getChunkAtChunkCoords(cx, cy, cz);
        if (c == null) {
            c = new Chunk(cx, cy, cz);
            c.initChunkData();
            zone.zoneGenerator.generateForChunkColumn(zone, new ChunkColumn(c.chunkX, c.chunkY, c.chunkZ));
            zone.addChunk(c);
        }

        x -= 16 * cx;
        y -= 16 * cy;
        z -= 16 * cz;
        return new BlockPosition(c, x, y, z);
    }

    public static BlockPosition getBlockPosAtVec(Zone zone, Vector3 vector3) {
        return getBlockPosAtVec(zone, (int) vector3.x, (int) vector3.y, (int) vector3.z);
    }

    public static Color blockLightToColor(short packedColor) {
        int red = packedColor >> 8;
        int green = (packedColor - (red << 8)) >> 4;
        int blue = ((packedColor - (red << 8)) - (green << 4));
        return new Color(red, green, blue, 255);
    }

    public static Vector3 blockLightToVec3(short packedColor) {
        int red = packedColor >> 8;
        int green = (packedColor - (red << 8)) >> 4;
        int blue = ((packedColor - (red << 8)) - (green << 4));
        return new Vector3(red, green, blue);
    }

    public static Vector4 blockLightToVec4(short packedColor) {
        return new Vector4(blockLightToVec3(packedColor), 255);
    }

}
