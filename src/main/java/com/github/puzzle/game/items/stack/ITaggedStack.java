package com.github.puzzle.game.items.stack;

import com.github.puzzle.game.items.data.DataTagManifest;

@Deprecated(forRemoval = true, since = "2.3.5")
public interface ITaggedStack {

    void puzzleLoader$setDataManifest(DataTagManifest tagManifest);
    DataTagManifest puzzleLoader$getDataManifest();

}
