package com.github.puzzle.game.engine;

import com.github.puzzle.game.ServerGlobals;

public interface ILoadingStage<T extends ILoadingEngine> {

    default T get() {
        return (T) ServerGlobals.ENGINE;
    }

}
