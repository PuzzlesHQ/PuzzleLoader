package io.github.puzzle.cosmic.api.network;

import com.badlogic.gdx.utils.ByteArray;
import io.github.puzzle.cosmic.util.annotation.Unfinished;

@Unfinished
public interface IPacketSerializer {

    ByteArray getBackingArray();

}
