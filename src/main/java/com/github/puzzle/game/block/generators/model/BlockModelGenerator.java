package com.github.puzzle.game.block.generators.model;

import com.badlogic.gdx.graphics.Pixmap;
import com.github.puzzle.core.Constants;
import com.github.puzzle.core.loader.meta.EnvType;
import com.github.puzzle.game.ServerGlobals;
import com.github.puzzle.game.engine.blocks.IBlockLoader;
import com.github.puzzle.game.engine.blocks.model.IBlockModelGenerator;
import finalforeach.cosmicreach.util.Identifier;
import finalforeach.cosmicreach.util.constants.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockModelGenerator implements IBlockModelGenerator {

    public static class Cuboid {

        public static final int LOCAL_NEG_X = 0;
        public static final int LOCAL_POS_X = 1;
        public static final int LOCAL_NEG_Y = 2;
        public static final int LOCAL_POS_Y = 3;
        public static final int LOCAL_NEG_Z = 4;
        public static final int LOCAL_POS_Z = 5;

        public static class Face {
            public String id = "";

            public String texture = "all";
            public float u1 = 0, v1 = 0, u2 = 16, v2 = 16;
            public int uvRotation = 0;
            public boolean ambientOcclusion = true;
            public boolean cullFace = true;

            public void setUVs(float u1, float v1, float u2, float v2) {
                this.u1 = u1;
                this.v1 = v1;
                this.u2 = u2;
                this.v2 = v2;
            }

        }

        public float x1, y1, z1;
        public float x2, y2, z2;
        public final Face[] faces = new Face[6];

        public Cuboid(float x1, float y1, float z1, float x2, float y2, float z2) {
            this.x1 = x1;
            this.y1 = y1;
            this.z1 = z1;
            this.x2 = x2;
            this.y2 = y2;
            this.z2 = z2;

            this.faces[Cuboid.LOCAL_NEG_X] = new Face();
            this.faces[Cuboid.LOCAL_NEG_X].id = "localNegX";

            this.faces[Cuboid.LOCAL_POS_X] = new Face();
            this.faces[Cuboid.LOCAL_POS_X].id = "localPosX";

            this.faces[Cuboid.LOCAL_NEG_Y] = new Face();
            this.faces[Cuboid.LOCAL_NEG_Y].id = "localNegY";

            this.faces[Cuboid.LOCAL_POS_Y] = new Face();
            this.faces[Cuboid.LOCAL_POS_Y].id = "localPosY";

            this.faces[Cuboid.LOCAL_NEG_Z] = new Face();
            this.faces[Cuboid.LOCAL_NEG_Z].id = "localNegZ";

            this.faces[Cuboid.LOCAL_POS_Z] = new Face();
            this.faces[Cuboid.LOCAL_POS_Z].id = "localPosZ";

            setAmbientOcclusion(true);
        }

        public Face face(Direction direction) {
            return faces[direction.ordinal()];
        }

        public void setAmbientOcclusion(boolean on) {
            for(Face face : faces) face.ambientOcclusion = on;
        }

        public void setCullFace(boolean on) {
            for(Face face : faces) face.cullFace = on;
        }

        public void setUVs(float u1, float v1, float u2, float v2) {
            for(Face face : faces) face.setUVs(u1, v1, u2, v2);
        }

        public void setUVRotation(int uvRotation) {
            for(Face face : faces) face.uvRotation = uvRotation;
        }

    }

    public static String getModelName(Identifier blockId, String modelName) {
        return blockId.toString() + "_" + modelName;
    }

    public static String getTextureName(Identifier blockId, String modelName, String textureName) {
        return blockId.toString() + "_" + modelName + "_" + textureName;
    }

    public Identifier blockId;
    public String modelName;

    public Map<String, Identifier> vanillaTextures = new HashMap<>();
//    public Map<String, Pixmap> customTextures = new HashMap<>();

    public List<Cuboid> cuboids = new ArrayList<>();

    public BlockModelGenerator(Identifier blockId, String modelName) {
        this.blockId = blockId;
        this.modelName = modelName;
    }

    public String getModelTextureName(String textureName) {
        return getTextureName(blockId, modelName, textureName);
    }

    public void createTexture(String textureName, Identifier file) {
        vanillaTextures.put(textureName, file);
    }

    public void createTexture(String textureName, Pixmap texture) {
//        customTextures.put(textureName, texture);
    }

    public Cuboid createCuboid(float x1, float y1, float z1, float x2, float y2, float z2) {
        Cuboid cuboid = new Cuboid(x1, y1, z1, x2, y2, z2);
        cuboids.add(cuboid);
        return cuboid;
    }

    public Cuboid createCuboid(float x1, float y1, float z1, float x2, float y2, float z2, String texture) {
        Cuboid cuboid = new Cuboid(x1, y1, z1, x2, y2, z2);
        cuboid.faces[Cuboid.LOCAL_POS_Y].texture = texture;
        cuboid.faces[Cuboid.LOCAL_NEG_Y].texture = texture;
        cuboid.faces[Cuboid.LOCAL_POS_X].texture = texture;
        cuboid.faces[Cuboid.LOCAL_NEG_X].texture = texture;
        cuboid.faces[Cuboid.LOCAL_POS_Z].texture = texture;
        cuboid.faces[Cuboid.LOCAL_NEG_Z].texture = texture;
        cuboids.add(cuboid);
        return cuboid;
    }

    public Cuboid createCuboid(float x1, float y1, float z1, float x2, float y2, float z2, String top, String bottom, String side) {
        Cuboid cuboid = new Cuboid(x1, y1, z1, x2, y2, z2);
        cuboid.faces[Cuboid.LOCAL_POS_Y].texture = top;
        cuboid.faces[Cuboid.LOCAL_NEG_Y].texture = bottom;
        cuboid.faces[Cuboid.LOCAL_POS_X].texture = side;
        cuboid.faces[Cuboid.LOCAL_NEG_X].texture = side;
        cuboid.faces[Cuboid.LOCAL_POS_Z].texture = side;
        cuboid.faces[Cuboid.LOCAL_NEG_Z].texture = side;
        cuboids.add(cuboid);
        return cuboid;
    }

    public Cuboid createCuboid(float x1, float y1, float z1, float x2, float y2, float z2, String top, String bottom, String side, String front) {
        Cuboid cuboid = new Cuboid(x1, y1, z1, x2, y2, z2);
        cuboid.faces[Cuboid.LOCAL_POS_Y].texture = top;
        cuboid.faces[Cuboid.LOCAL_NEG_Y].texture = bottom;
        cuboid.faces[Cuboid.LOCAL_POS_X].texture = side;
        cuboid.faces[Cuboid.LOCAL_NEG_X].texture = side;
        cuboid.faces[Cuboid.LOCAL_POS_Z].texture = front;
        cuboid.faces[Cuboid.LOCAL_NEG_Z].texture = side;
        cuboids.add(cuboid);
        return cuboid;
    }

    public Cuboid createCuboid(float x1, float y1, float z1, float x2, float y2, float z2, String top, String bottom, String left, String right, String front, String back) {
        Cuboid cuboid = new Cuboid(x1, y1, z1, x2, y2, z2);
        cuboid.faces[Cuboid.LOCAL_POS_Y].texture = top;
        cuboid.faces[Cuboid.LOCAL_NEG_Y].texture = bottom;
        cuboid.faces[Cuboid.LOCAL_POS_X].texture = right;
        cuboid.faces[Cuboid.LOCAL_NEG_X].texture = left;
        cuboid.faces[Cuboid.LOCAL_POS_Z].texture = front;
        cuboid.faces[Cuboid.LOCAL_NEG_Z].texture = back;
        cuboids.add(cuboid);
        return cuboid;
    }

    @Override
    public void register(IBlockLoader loader) {
//        for(String customTextureName : customTextures.keySet()) {
//            loader.registerTexture(getModelTextureName(customTextureName), customTextures.get(customTextureName));
//        }

        if(Constants.SIDE != EnvType.SERVER) {
            for (String vanillaTextureName : vanillaTextures.keySet()) {
                loader.registerTexture(vanillaTextures.get(vanillaTextureName));
            }
        }
    }

    @Override
    public String getModelName() {
        return getModelName(blockId, modelName);
    }

    @Override
    public String generateJson() {
        return ServerGlobals.MODEL_GENERATOR.fromGeneratorAsString(this);
    }
}
