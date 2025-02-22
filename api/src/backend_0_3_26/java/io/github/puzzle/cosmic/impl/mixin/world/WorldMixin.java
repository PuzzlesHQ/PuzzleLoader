package io.github.puzzle.cosmic.impl.mixin.world;

import finalforeach.cosmicreach.world.World;
import finalforeach.cosmicreach.world.Zone;
import finalforeach.cosmicreach.worldgen.ZoneGenerator;
import io.github.puzzle.cosmic.api.world.IPuzzleWorld;
import io.github.puzzle.cosmic.api.world.IPuzzleZone;
import io.github.puzzle.cosmic.util.annotation.Internal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Collection;
import java.util.Set;

@Internal
@Mixin(World.class)
public class WorldMixin implements IPuzzleWorld {

    @Unique
    private final transient World puzzleLoader$world = IPuzzleWorld.as(this);

    @Unique
    private final transient IZoneMap puzzleLoader$zoneMap = new IZoneMap() {
        @Override
        public IPuzzleZone get(String s) {
            return IPuzzleZone.as(puzzleLoader$world.getZoneIfExists(s));
        }

        @Override
        public IPuzzleZone getOrCreate(String s) {
            return IPuzzleZone.as(puzzleLoader$world.getZoneCreateIfNull(s));
        }

        @Override
        public void put(IPuzzleZone iPuzzleZone) {
            puzzleLoader$world.addZone(iPuzzleZone.as());
        }

        @Override
        public void put(String s, ZoneGenerator zoneGenerator) {
            puzzleLoader$world.addNewZone(s, zoneGenerator);
        }

        @Override
        public Set<String> getKeys() {
            return puzzleLoader$world.getZoneIds();
        }

        @Override
        public Collection<Zone> getValues() {
            return puzzleLoader$world.getZones();
        }
    };

    @Override
    public String _getDisplayName() {
        return puzzleLoader$world.getDisplayName();
    }

    @Override
    public IZoneMap _getZoneMap() {
        return puzzleLoader$zoneMap;
    }

    @Override
    public String _getAbsolutePath() {
        return puzzleLoader$world.getFullSaveFolder();
    }

    @Override
    public String _getFolderName() {
        return puzzleLoader$world.getWorldFolderName();
    }

    @Override
    public long _getDayNumber() {
        return puzzleLoader$world.getDayNumber();
    }

    @Override
    public long _getCurrentWorldTick() {
        return puzzleLoader$world.getCurrentWorldTick();
    }

    @Override
    public IPuzzleZone _getDefaultZone() {
        return IPuzzleZone.as(puzzleLoader$world.getDefaultZone());
    }
}
