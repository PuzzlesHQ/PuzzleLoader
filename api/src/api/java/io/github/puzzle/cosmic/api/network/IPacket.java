package io.github.puzzle.cosmic.api.network;

public interface IPacket {

    void read(IPacketDeserializer serializer);
    void write(IPacketSerializer deserializer);

}
