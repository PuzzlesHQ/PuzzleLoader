package io.github.puzzle.cosmic.api.network;

import io.github.puzzle.cosmic.util.annotation.Unfinished;

/**
 *
 * @author Mr_Zombii
 * @since 0.3.26
 */
@Unfinished
public interface IPacket {

    void read(IPacketDeserializer serializer);
    void write(IPacketSerializer deserializer);

}
