package net.brokenmoon.afloydwiremod.packet;

import net.brokenmoon.afloydwiremod.mixinInterfaces.INetHandler;
import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class WiremodProgrammerGuiPacket extends Packet {
    public int mode;
    public int x;
    public int y;
    public int z;
    public WiremodProgrammerGuiPacket(){

    }

    public WiremodProgrammerGuiPacket(int i, int x, int y, int z){
        this.mode = i;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void readPacketData(DataInputStream dataInputStream) throws IOException {
        this.mode = dataInputStream.readInt();
        this.x = dataInputStream.readInt();
        this.y = dataInputStream.readInt();
        this.z = dataInputStream.readInt();

    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(this.mode);
        dataOutputStream.writeInt(this.x);
        dataOutputStream.writeInt(this.y);
        dataOutputStream.writeInt(this.z);
    }

    @Override
    public void processPacket(NetHandler netHandler) {
        ((INetHandler)netHandler).wiremodHandleOpenProgrammerGUI(this);
    }

    @Override
    public int getPacketSize() {
        return 4 * 4;
    }
}
