package net.brokenmoon.afloydwiremod.packet;

import net.brokenmoon.afloydwiremod.mixinInterfaces.INetHandler;
import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class WiremodWiringPacket extends Packet {
    public int type;
    public int x1;
    public int y1;
    public int z1;
    public int x2;
    public int y2;
    public int z2;
    public int slot1;
    public int slot2;
    public ArrayList<Integer> xadd;
    public ArrayList<Integer> yadd;
    public ArrayList<Integer> zadd;
    public ArrayList<Integer> sideadd;
    public boolean backwired;
    public float red = 1.0f;
    public float green = 0f;
    public float blue = 0f;
    public float alpha = 1f;
    public float width = 0.5f;
    private int size = 0;
    public WiremodWiringPacket(){
    }
    public WiremodWiringPacket(int x1, int y1, int z1, int x2, int y2, int z2, int slot1, int slot2, ArrayList<Integer> xadd, ArrayList<Integer> yadd, ArrayList<Integer> zadd, ArrayList<Integer> sideadd, boolean b, float red, float green, float blue, float alpha, float width) {
        this.type = 0;
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
        this.slot1 = slot1;
        this.slot2 = slot2;
        this.xadd = xadd;
        this.yadd = yadd;
        this.zadd = zadd;
        this.sideadd = sideadd;
        this.backwired = b;
        this.red = red;
        this.blue = blue;
        this.green = green;
        this.alpha = alpha;
        this.width = width;
    }

    public WiremodWiringPacket(int x1, int y1, int z1, int x2, int y2, int z2, int slot1, int slot2) {
        this.type = 1;
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
        this.slot1 = slot1;
        this.slot2 = slot2;
    }

    @Override
    public void readPacketData(DataInputStream dataInputStream) throws IOException {
        this.type = dataInputStream.readInt();
        this.x1 = dataInputStream.readInt();
        this.y1 = dataInputStream.readInt();
        this.z1 = dataInputStream.readInt();
        this.x2 = dataInputStream.readInt();
        this.y2 = dataInputStream.readInt();
        this.z2 = dataInputStream.readInt();
        this.slot1 = dataInputStream.readInt();
        this.slot2 = dataInputStream.readInt();
        backwired = dataInputStream.readBoolean();
        red = dataInputStream.readFloat();
        green = dataInputStream.readFloat();
        blue = dataInputStream.readFloat();
        alpha = dataInputStream.readFloat();
        width = dataInputStream.readFloat();
        boolean isxadd = dataInputStream.readBoolean();
        xadd = new ArrayList<>();
        yadd = new ArrayList<>();
        zadd = new ArrayList<>();
        sideadd = new ArrayList<>();
        if(isxadd){
            int length = dataInputStream.readInt();
            for(int i = 0; i < length; i++) {
                xadd.add(dataInputStream.readInt());
                yadd.add(dataInputStream.readInt());
                zadd.add(dataInputStream.readInt());
                sideadd.add(dataInputStream.readInt());
            }
        }
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(type);
        dataOutputStream.writeInt(x1);
        dataOutputStream.writeInt(y1);
        dataOutputStream.writeInt(z1);
        dataOutputStream.writeInt(x2);
        dataOutputStream.writeInt(y2);
        dataOutputStream.writeInt(z2);
        dataOutputStream.writeInt(slot1);
        dataOutputStream.writeInt(slot2);
        dataOutputStream.writeBoolean(backwired);
        dataOutputStream.writeFloat(red);
        dataOutputStream.writeFloat(green);
        dataOutputStream.writeFloat(blue);
        dataOutputStream.writeFloat(alpha);
        dataOutputStream.writeFloat(width);
        dataOutputStream.writeBoolean(xadd != null);
        if(xadd != null){
            dataOutputStream.writeInt(xadd.size());
            size = 4;
            for(int i = 0; i < xadd.size(); i++){
                dataOutputStream.writeInt(xadd.get(i));
                size += 4;
                dataOutputStream.writeInt(yadd.get(i));
                size += 4;
                dataOutputStream.writeInt(zadd.get(i));
                size += 4;
                dataOutputStream.writeInt(sideadd.get(i));
                size += 4;
            }
        }
    }

    @Override
    public void processPacket(NetHandler netHandler) {
        ((INetHandler)netHandler).wiremodHandleWireChips(this);
    }

    @Override
    public int getPacketSize() {
        return 14 * 4 + 2 + size;
    }
}
