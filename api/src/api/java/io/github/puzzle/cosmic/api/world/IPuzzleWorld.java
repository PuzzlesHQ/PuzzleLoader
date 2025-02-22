package io.github.puzzle.cosmic.api.world;

import io.github.puzzle.cosmic.api.constants.ZoneGenTmpClass;
import io.github.puzzle.cosmic.api.constants.ZoneTmpClass;
import io.github.puzzle.cosmic.util.annotation.Note;
import io.github.puzzle.cosmic.util.annotation.Unfinished;
import io.github.puzzle.cosmic.util.annotation.compile.ApiGen;

import java.util.Collection;
import java.util.Set;

/**
 *
 * @author Mr_Zombii
 * @since 0.3.26
 */
@Note("A lot of zone addition or creation methods on the world class got redirected/relocated into the new class called IZoneMap for API cleanliness.")
@ApiGen("World")
public interface IPuzzleWorld {

    String _getDisplayName();

    IZoneMap _getZoneMap();
    IPuzzleZone _getDefaultZone();

    String _getAbsolutePath();
    String _getFolderName();

    long _getCurrentWorldTick();
    long _getDayNumber();

    boolean _canEnter();

    interface IZoneMap {

        IPuzzleZone get(String stringId);
        IPuzzleZone getOrCreate(String stringId);

        void put(IPuzzleZone zone);
        void put(String zoneId, ZoneGenTmpClass zoneGenerator);

        Set<String> getKeys();
        Collection<ZoneTmpClass> getValues();

    }

}
