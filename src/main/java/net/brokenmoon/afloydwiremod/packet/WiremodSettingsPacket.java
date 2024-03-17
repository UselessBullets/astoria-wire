package net.brokenmoon.afloydwiremod.packet;

import net.brokenmoon.afloydwiremod.mixinInterfaces.INetHandler;
import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class WiremodSettingsPacket extends Packet {
    public int mode;
    public float value;
    public int size;
    public int x;
    public int y;
    public int z;
    public WiremodSettingsPacket(){

    }

    //Settings which changes one variable
    public WiremodSettingsPacket(int mode, float amount, int x, int y, int z) {
        this.mode = mode;
        this.value = amount;
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
        if(mode == 0 || mode == 1){
            this.value = dataInputStream.readFloat();
        }
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(mode);
        dataOutputStream.writeInt(x);
        dataOutputStream.writeInt(y);
        dataOutputStream.writeInt(z);
        if(mode == 0 || mode == 1){
            dataOutputStream.writeFloat(value);
            size += 4;
        }
    }

    @Override
    public void processPacket(NetHandler netHandler) {
        ((INetHandler)netHandler).wiremodHandleSettings(this);
    }

    @Override
    public int getPacketSize() {
        return 16 + size;
    }
}
