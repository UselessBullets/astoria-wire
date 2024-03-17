package net.brokenmoon.afloydwiremod.packet;

import net.brokenmoon.afloydwiremod.api.WireConnection;
import net.brokenmoon.afloydwiremod.gui.WiringButton;
import net.brokenmoon.afloydwiremod.mixinInterfaces.INetHandler;
import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class WiremodPacketSyncIO extends Packet {
    public int x;
    public int y;
    public int z;
    private int size;
    public WiringButton[] io;
    public WiringButton[] oi;
    public String mode;
    public int tickAmount;
    public WiremodPacketSyncIO(){
        this.isChunkDataPacket = true;
    }
    public WiremodPacketSyncIO(int xCoord, int yCoord, int zCoord, WiringButton[] inputs, WiringButton[] outputs) {
        this.isChunkDataPacket = true;
        this.x = xCoord;
        this.y = yCoord;
        this.z = zCoord;
        this.io = inputs;
        this.oi = outputs;
    }

    public WiremodPacketSyncIO(int xCoord, int yCoord, int zCoord, WiringButton[] inputs, WiringButton[] outputs, String mode, int tickamount) {
        this.isChunkDataPacket = true;
        this.x = xCoord;
        this.y = yCoord;
        this.z = zCoord;
        this.io = inputs;
        this.oi = outputs;
        this.mode = mode;
        this.tickAmount = tickamount;
    }

    @Override
    public void readPacketData(DataInputStream dataInputStream) throws IOException {
        this.x = dataInputStream.readInt();
        this.y = dataInputStream.readInt();
        this.z = dataInputStream.readInt();
        if(dataInputStream.readBoolean()){
            //INPUT
            int length = dataInputStream.readInt();
            io = new WiringButton[length];
            for(int i = 0; i < length; ++i){
                io[i] = new WiringButton();
                io[i].x = dataInputStream.readInt();
                io[i].y = dataInputStream.readInt();
                io[i].slot = dataInputStream.readInt();
                io[i].buttonString = dataInputStream.readUTF();
                io[i].floatvalue = dataInputStream.readFloat();
                io[i].stringvalue = dataInputStream.readUTF();
                if(dataInputStream.readBoolean()){
                    this.io[i].wire = new WireConnection();
                    this.io[i].wire.x = dataInputStream.readInt();
                    this.io[i].wire.y = dataInputStream.readInt();
                    this.io[i].wire.z = dataInputStream.readInt();
                    this.io[i].wire.thisslot = dataInputStream.readInt();
                    this.io[i].wire.thatslot = dataInputStream.readInt();
                    this.io[i].wire.red = dataInputStream.readFloat();
                    this.io[i].wire.green = dataInputStream.readFloat();
                    this.io[i].wire.blue = dataInputStream.readFloat();
                    this.io[i].wire.alpha = dataInputStream.readFloat();
                    this.io[i].wire.width = dataInputStream.readFloat();
                    this.io[i].wire.backwired = dataInputStream.readBoolean();
                    this.io[i].wire.isMade = dataInputStream.readBoolean();
                    if(dataInputStream.readBoolean()){
                        int ixlength = dataInputStream.readInt();
                        for(int ix = 0; ix < ixlength; ix++){
                            this.io[i].wire.xadd.add(dataInputStream.readInt());
                            this.io[i].wire.yadd.add(dataInputStream.readInt());
                            this.io[i].wire.zadd.add(dataInputStream.readInt());
                            this.io[i].wire.sideadd.add(dataInputStream.readInt());
                        }
                    }
                }
            }
        }
        if(dataInputStream.readBoolean()){
            int length = dataInputStream.readInt();
            oi = new WiringButton[length];
            for(int i = 0; i < length; ++i){
                oi[i] = new WiringButton();
                oi[i].x = dataInputStream.readInt();
                oi[i].y = dataInputStream.readInt();
                oi[i].slot = dataInputStream.readInt();
                oi[i].buttonString = dataInputStream.readUTF();
                oi[i].floatvalue = dataInputStream.readFloat();
                oi[i].stringvalue = dataInputStream.readUTF();
                if(dataInputStream.readBoolean()){
                    this.oi[i].wire = new WireConnection();
                    this.oi[i].wire.x = dataInputStream.readInt();
                    this.oi[i].wire.y = dataInputStream.readInt();
                    this.oi[i].wire.z = dataInputStream.readInt();
                    this.oi[i].wire.thisslot = dataInputStream.readInt();
                    this.oi[i].wire.thatslot = dataInputStream.readInt();
                    this.oi[i].wire.red = dataInputStream.readFloat();
                    this.oi[i].wire.green = dataInputStream.readFloat();
                    this.oi[i].wire.blue = dataInputStream.readFloat();
                    this.oi[i].wire.alpha = dataInputStream.readFloat();
                    this.oi[i].wire.width = dataInputStream.readFloat();
                    this.oi[i].wire.backwired = dataInputStream.readBoolean();
                    this.oi[i].wire.isMade = dataInputStream.readBoolean();
                    if(dataInputStream.readBoolean()){
                        int ixlength = dataInputStream.readInt();
                        for(int ix = 0; ix < ixlength; ix++){
                            this.oi[i].wire.xadd.add(dataInputStream.readInt());
                            this.oi[i].wire.yadd.add(dataInputStream.readInt());
                            this.oi[i].wire.zadd.add(dataInputStream.readInt());
                            this.oi[i].wire.sideadd.add(dataInputStream.readInt());
                        }
                    }
                }
            }
        }
        if(dataInputStream.readBoolean()){
            this.mode = dataInputStream.readUTF();
        }
        if(dataInputStream.readBoolean()){
            this.tickAmount = dataInputStream.readInt();
        }
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(x);
        dataOutputStream.writeInt(y);
        dataOutputStream.writeInt(z);
        size = size + 4;
        size = size + 4;
        size = size + 4;
        if(io != null){
            dataOutputStream.writeBoolean(true);
            size++;
            dataOutputStream.writeInt(io.length);
            size = size + 4;
            for(int i = 0; i < this.io.length; ++i){
                dataOutputStream.writeInt(this.io[i].x);
                size = size + 4;
                dataOutputStream.writeInt(this.io[i].y);
                size = size + 4;
                dataOutputStream.writeInt(this.io[i].slot);
                size = size + 4;
                dataOutputStream.writeUTF(this.io[i].buttonString);
                size = size + this.io[i].buttonString.length();
                dataOutputStream.writeFloat(this.io[i].floatvalue);
                size = size + 4;
                dataOutputStream.writeUTF(this.io[i].stringvalue);
                size = size + this.io[i].stringvalue.length();
                if(io[i].wire != null){
                    dataOutputStream.writeBoolean(true);
                    size++;
                    dataOutputStream.writeInt(this.io[i].wire.x);
                    dataOutputStream.writeInt(this.io[i].wire.y);
                    dataOutputStream.writeInt(this.io[i].wire.z);
                    size = size + 4;
                    size = size + 4;
                    size = size + 4;
                    dataOutputStream.writeInt(this.io[i].wire.thisslot);
                    dataOutputStream.writeInt(this.io[i].wire.thatslot);
                    dataOutputStream.writeFloat(this.io[i].wire.red);
                    size = size + 4;
                    size = size + 4;
                    size = size + 4;
                    dataOutputStream.writeFloat(this.io[i].wire.green);
                    dataOutputStream.writeFloat(this.io[i].wire.blue);
                    dataOutputStream.writeFloat(this.io[i].wire.alpha);
                    size = size + 4;
                    size = size + 4;
                    size = size + 4;
                    dataOutputStream.writeFloat(this.io[i].wire.width);
                    dataOutputStream.writeBoolean(this.io[i].wire.backwired);
                    dataOutputStream.writeBoolean(this.io[i].wire.isMade);
                    size = size + 4;
                    size++;
                    size++;
                    if(this.io[i].wire.xadd != null){
                        dataOutputStream.writeBoolean(true);
                        size++;
                        dataOutputStream.writeInt(this.io[i].wire.xadd.size());
                        size = size + 4;
                        for(int ix = 0; ix < this.io[i].wire.xadd.size(); ix++){
                            dataOutputStream.writeInt(this.io[i].wire.xadd.get(ix));
                            dataOutputStream.writeInt(this.io[i].wire.yadd.get(ix));
                            dataOutputStream.writeInt(this.io[i].wire.zadd.get(ix));
                            dataOutputStream.writeInt(this.io[i].wire.sideadd.get(ix));
                            size = size + 4;
                            size = size + 4;
                            size = size + 4;
                            size = size + 4;
                        }
                    } else {
                        dataOutputStream.writeBoolean(false);
                        size++;
                    }
                } else{
                    dataOutputStream.writeBoolean(false);
                    size++;
                }
            }
        } else {
            dataOutputStream.writeBoolean(false);
            size++;
        }
        if(oi != null){
            dataOutputStream.writeBoolean(true);
            size++;
            dataOutputStream.writeInt(oi.length);
            size = size + 4;
            for(int i = 0; i < this.oi.length; ++i){
                dataOutputStream.writeInt(this.oi[i].x);
                size = size + 4;
                dataOutputStream.writeInt(this.oi[i].y);
                size = size + 4;
                dataOutputStream.writeInt(this.oi[i].slot);
                size = size + 4;
                dataOutputStream.writeUTF(this.oi[i].buttonString);
                size = size + this.oi[i].buttonString.length();
                dataOutputStream.writeFloat(this.oi[i].floatvalue);
                size = size + 4;
                dataOutputStream.writeUTF(this.oi[i].stringvalue);
                size = size + this.oi[i].stringvalue.length();
                if(oi[i].wire != null){
                    dataOutputStream.writeBoolean(true);
                    size++;
                    dataOutputStream.writeInt(this.oi[i].wire.x);
                    dataOutputStream.writeInt(this.oi[i].wire.y);
                    dataOutputStream.writeInt(this.oi[i].wire.z);
                    size = size + 4;
                    size = size + 4;
                    size = size + 4;
                    dataOutputStream.writeInt(this.oi[i].wire.thisslot);
                    dataOutputStream.writeInt(this.oi[i].wire.thatslot);
                    dataOutputStream.writeFloat(this.oi[i].wire.red);
                    size = size + 4;
                    size = size + 4;
                    size = size + 4;
                    dataOutputStream.writeFloat(this.oi[i].wire.green);
                    dataOutputStream.writeFloat(this.oi[i].wire.blue);
                    dataOutputStream.writeFloat(this.oi[i].wire.alpha);
                    size = size + 4;
                    size = size + 4;
                    size = size + 4;
                    dataOutputStream.writeFloat(this.oi[i].wire.width);
                    dataOutputStream.writeBoolean(this.oi[i].wire.backwired);
                    dataOutputStream.writeBoolean(this.oi[i].wire.isMade);
                    size = size + 4;
                    size++;
                    size++;
                    if(this.oi[i].wire.xadd != null){
                        dataOutputStream.writeBoolean(true);
                        size++;
                        dataOutputStream.writeInt(this.oi[i].wire.xadd.size());
                        size = size + 4;
                        for(int ix = 0; ix < this.oi[i].wire.xadd.size(); ix++){
                            dataOutputStream.writeInt(this.oi[i].wire.xadd.get(ix));
                            dataOutputStream.writeInt(this.oi[i].wire.yadd.get(ix));
                            dataOutputStream.writeInt(this.oi[i].wire.zadd.get(ix));
                            dataOutputStream.writeInt(this.oi[i].wire.sideadd.get(ix));
                            size = size + 4;
                            size = size + 4;
                            size = size + 4;
                            size = size + 4;
                        }
                    } else {
                        dataOutputStream.writeBoolean(false);
                        size++;
                    }
                } else{
                    dataOutputStream.writeBoolean(false);
                    size++;
                }
            }
        } else {
            dataOutputStream.writeBoolean(false);
            size++;
        }
        dataOutputStream.writeBoolean(this.mode != null);
        if(this.mode != null){
            dataOutputStream.writeUTF(mode);
        }
        dataOutputStream.writeBoolean(this.tickAmount != 0);
        if(this.tickAmount != 0){
            dataOutputStream.writeInt(tickAmount);
        }
    }

    @Override
    public void processPacket(NetHandler netHandler) {
        ((INetHandler)netHandler).wiremodHandleIODisc(this);
    }

    @Override
    public int getPacketSize() {
        return size;
    }
}
