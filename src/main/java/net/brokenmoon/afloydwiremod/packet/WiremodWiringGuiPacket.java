package net.brokenmoon.afloydwiremod.packet;

import net.brokenmoon.afloydwiremod.mixinInterfaces.INetHandler;
import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class WiremodWiringGuiPacket extends Packet {
    public int x;
    public int y;
    public int z;
    public int x2;
    public int y2;
    public int z2;
    public WiremodWiringGuiPacket(){

    }
    public WiremodWiringGuiPacket(int x, int y, int z, int x2, int y2, int z2){
        this.x = x;
        this.y = y;
        this.z = z;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
    }
    @Override
    public void readPacketData(DataInputStream dataInputStream) throws IOException {
        this.x = dataInputStream.readInt();
        this.y = dataInputStream.readInt();
        this.z = dataInputStream.readInt();
        this.x2 = dataInputStream.readInt();
        this.y2 = dataInputStream.readInt();
        this.z2 = dataInputStream.readInt();
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(this.x);
        dataOutputStream.writeInt(this.y);
        dataOutputStream.writeInt(this.z);
        dataOutputStream.writeInt(this.x2);
        dataOutputStream.writeInt(this.y2);
        dataOutputStream.writeInt(this.z2);
    }

    @Override
    public void processPacket(NetHandler netHandler) {
        ((INetHandler)netHandler).wiremodHandleOpenWiringGUI(this);
    }

    @Override
    public int getPacketSize() {
        return 6 * 4;
    }
}
