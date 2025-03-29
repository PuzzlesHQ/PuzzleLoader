package com.github.puzzle.game.ui.surface.element;

import com.badlogic.gdx.graphics.Texture;
import com.github.puzzle.game.ui.surface.Surface;
import finalforeach.cosmicreach.util.Identifier;

public class PulsingImageElement extends ImageElement {

    int pulseTimer = 100;
    int pulse = 15;

    float pulseInc = (1f / pulse) / pulseTimer;

    boolean reverse = false;
    int pulseCounter = pulseTimer;

    public PulsingImageElement(Texture texture) {
        super(texture);
    }

    public PulsingImageElement(Identifier location) {
        super(location);
    }

    @Override
    public void update(Surface surface, float delta) {
        scale = 1 + (pulseInc * pulseCounter);

        pulseCounter += reverse ? 1 : -1;
        if (pulseCounter < 1) {
            reverse = true;
        }
        if (pulseCounter > pulseTimer) {
            reverse = false;
        }
    }

    public void setPulseTimer(int pulseTimer) {
        this.pulseTimer = pulseTimer;
    }

    public void setPulseIntensity(int pulse) {
        this.pulse = pulse;
    }

    public int getPulseIntensity() {
        return pulse;
    }

    public int getPulseTimer() {
        return pulseTimer;
    }
}
