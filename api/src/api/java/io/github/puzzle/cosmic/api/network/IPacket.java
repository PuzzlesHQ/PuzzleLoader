package io.github.puzzle.cosmic.api.network;

import io.github.puzzle.cosmic.util.annotation.Unfinished;

@Unfinished
public interface IPacket {

    void read(IPacketDeserializer serializer);
    void write(IPacketSerializer deserializer);

}
