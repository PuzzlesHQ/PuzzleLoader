package com.github.puzzle.game.ui.screens;

import finalforeach.cosmicreach.items.ISlotContainerParent;
import finalforeach.cosmicreach.ui.screens.BaseItemScreen;

public class BasePuzzleScreen extends BaseItemScreen {

    public BasePuzzleScreen(int windowId, ISlotContainerParent parent) {
        super(windowId, parent);
    }

    @Override
    public void drawItems() {
        onRender();
        super.drawItems();
    }

    public void onRender() {}

    @Override
    public void onRemove() {
        onClose();
    }

    public void onOpen() {}
    public void onClose() {}
}
