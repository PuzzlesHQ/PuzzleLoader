package com.github.puzzle.game.engine;

import com.badlogic.gdx.graphics.Color;
import com.github.puzzle.core.loader.engine.GameLoader;
import com.github.puzzle.game.ui.surface.Surface;
import com.github.puzzle.game.ui.surface.SurfaceImpl;
import com.github.puzzle.game.ui.surface.element.Element;
import com.github.puzzle.game.ui.surface.element.ImageElement;
import com.github.puzzle.game.ui.surface.element.ProgressBarElement;
import com.github.puzzle.game.ui.surface.element.PulsingImageElement;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.PrealphaPreamble;
import finalforeach.cosmicreach.util.Identifier;

import javax.swing.*;

public class GameBootingScreen extends SurfaceImpl {

    GameLoader loader = new GameLoader();

    ImageElement crLogo;
    ImageElement pzLogo;
    ProgressBarElement ramUsageBar;

    ProgressBarElement bar1;
    ProgressBarElement bar2;
    ProgressBarElement bar3;

    public static final Color progressBarFGFull = new Color(0.027f, 0.388f, 0.039f, 1);
    public static final Color progressBarFGEmpty = new Color(.012f, 0.2f, 0.02f, 1);
    public static final Color progressBarBG = new Color(0.078f, 0.831f, 0.102f, 1);

    public static final Color ramProgressBarFGFull = new Color(0.671f, 0.671f, 0.671f, 1);
    public static final Color ramProgressBarFGEmpty = new Color(0.239f, 0.239f, 0.239f, 1);
    public static final Color ramProgressBarBG = new Color(1, 1, 1, 1);
    
    public GameBootingScreen() {
        super(null, null);
    }

    @Override
    public void init() {
        super.init();

        Runtime r = Runtime.getRuntime();
        int ramRange = (int) (r.maxMemory() / (1024 * 1024));

        float inc = 255f / ramRange;
        ramUsageBar = new ProgressBarElement(ramProgressBarFGFull, ramProgressBarFGEmpty, ramProgressBarBG) {
            @Override
            public void render(Surface surface) {
                super.render(surface);

                height = 21;

                int ramValue = (int) ((r.totalMemory() - r.freeMemory()) / (1024 * 1024));
                ramUsageBar.setProgress(ramValue);

                String clr = String.format("%02x%02x%02x", 255, 255 - (int) (inc * ramValue), 255 - (int) (inc * ramValue));
                setText(String.format("Memory Usage: §["+clr+"]%dmb§r / %d§rmb", ramValue, ramRange));
            }
        };
        ramUsageBar.setMax(ramRange);
        ramUsageBar.setY(5);
        ramUsageBar.setAnchorX(Element.AnchorX.CENTER);
        ramUsageBar.setAnchorY(Element.AnchorY.TOP);

        bar1 = new ProgressBarElement(progressBarFGFull, progressBarFGEmpty, progressBarBG);
        bar1.setAnchorX(Element.AnchorX.CENTER);
        bar1.setY(40);

        bar2 = new ProgressBarElement(progressBarFGFull, progressBarFGEmpty, progressBarBG);
        bar2.setAnchorX(Element.AnchorX.CENTER);
        bar2.setY(90);

        bar3 = new ProgressBarElement(progressBarFGFull, progressBarFGEmpty, progressBarBG);
        bar3.setAnchorX(Element.AnchorX.CENTER);
        bar3.setY(120);

        loader.bar1 = bar1;
        loader.bar2 = bar2;
        loader.bar3 = bar3;

        this.add(crLogo = new ImageElement(Identifier.of("base:textures/text-logo-hd.png")) {
            @Override
            public void render(Surface surface) {
                super.render(surface);

                float scaledWidth = (620) * scale;
                float scaledHeight = texHeight * scale;
                x = (int) (-scaledWidth);
                y = (int) (-scaledHeight * .5f) - 30;
            }
        });
        crLogo.scale = .8f;

        this.add(pzLogo = new PulsingImageElement(Identifier.of("puzzle-loader:icons/PuzzleLoaderIconx64.png")));
        pzLogo.setAnchorX(Element.AnchorX.RIGHT);
        pzLogo.setAnchorY(Element.AnchorY.BOTTOM);
        pzLogo.setX(-5);
        pzLogo.setY(-5);

        this.add(ramUsageBar);
        this.add(bar1);
        this.add(bar2);
        this.add(bar3);
    }

    byte canCreateGameLoader = 0;
    boolean readyToLoad = false;
    
    @Override
    public void render() {
        super.render();

        if (loader.finished.get()) {
            GameState.switchToGameState(new PrealphaPreamble());
            return;
        }

        if (readyToLoad && canCreateGameLoader == 0) {
            canCreateGameLoader = 1;
        }
        if (canCreateGameLoader == 1) {
            SwingUtilities.invokeLater(loader::create);
            canCreateGameLoader = 2;
        }
        readyToLoad = true;

        loader.update();
    }
}
