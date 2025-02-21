package io.github.puzzle.cosmic.api.network;

public interface IPacket {

    void read(IPacketSerializer serializer);
    void write(IPacketDeserializer deserializer);

}
