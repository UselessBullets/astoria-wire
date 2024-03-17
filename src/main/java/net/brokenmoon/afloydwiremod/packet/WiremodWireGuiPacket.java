package net.brokenmoon.afloydwiremod.packet;

import net.brokenmoon.afloydwiremod.mixinInterfaces.INetHandler;
import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class WiremodWireGuiPacket extends Packet {
    @Override
    public void readPacketData(DataInputStream dataInputStream) throws IOException {

    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream) throws IOException {

    }

    @Override
    public void processPacket(NetHandler netHandler) {
        ((INetHandler)netHandler).wiremodHandleWireToolSettingsGui(this);
    }

    @Override
    public int getPacketSize() {
        return 0;
    }
}
