package com.github.puzzle.game.items.impl;

import com.github.puzzle.game.items.IModItem;
import com.github.puzzle.game.items.data.DataTagManifest;
import finalforeach.cosmicreach.items.Item;
import finalforeach.cosmicreach.util.GameTagList;
import finalforeach.cosmicreach.util.IGameTagged;
import finalforeach.cosmicreach.util.Identifier;

public abstract class AbstractItem implements IGameTagged, Item, IModItem {

    public final Identifier id;
    public final GameTagList list;
    public final DataTagManifest manifest;

    public AbstractItem(Identifier id) {
        this.id = id;
        this.list = new GameTagList();
        this.manifest = new DataTagManifest();
    }

    @Override
    public GameTagList getTags() {
        return new GameTagList();
    }

    @Override
    public void initTagList() {
        list.add(IModItem.MODDED_ITEM_TAG);
    }

    @Override
    public DataTagManifest getTagManifest() {
        return manifest;
    }

    @Override
    public String getName() {
        return id.getName();
    }

    @Override
    public Identifier getIdentifier() {
        return id;
    }

    @Override
    public String getID() {
        return id.toString();
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
