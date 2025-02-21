package io.github.puzzle.cosmic.api.network;

import com.badlogic.gdx.utils.ByteArray;
import io.github.puzzle.cosmic.util.annotation.Unfinished;

/**
 *
 * @author Mr_Zombii
 * @since 0.3.26
 */
@Unfinished
public interface IPacketSerializer {

    ByteArray getBackingArray();

}
