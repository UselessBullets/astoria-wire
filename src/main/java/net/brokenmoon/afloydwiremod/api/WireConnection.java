package net.brokenmoon.afloydwiremod.api;

import com.mojang.nbt.CompoundTag;

import java.util.ArrayList;

public class WireConnection {
    public boolean isMade;
    public int x;
    public int y;
    public int z;
    public int thisslot;
    public int thatslot;
    public ArrayList<Integer> xadd = new ArrayList<Integer>();
    public ArrayList<Integer> yadd = new ArrayList<Integer>();
    public ArrayList<Integer> zadd = new ArrayList<Integer>();
    public ArrayList<Integer> sideadd = new ArrayList<Integer>();
    public boolean backwired = false;
    public float red = 1;
    public float green = 0;
    public float blue = 0;
    public float alpha = 1;
    public float width = 0.5f;

    public WireConnection(){
        this.isMade = false;
    }

    public WireConnection(int x, int y, int z, int slot, int slot1, ArrayList<Integer> xadd, ArrayList<Integer> yadd, ArrayList<Integer> zadd, ArrayList<Integer> sideadd, boolean backwired, float red, float green, float blue, float alpha, float width) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.thisslot = slot;
        this.thatslot = slot1;
        this.xadd = xadd;
        this.yadd = yadd;
        this.zadd = zadd;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
        this.width = width;
        this.sideadd = sideadd;
        this.backwired = backwired;
        this.isMade = true;
    }

    public WireConnection(int xCoord, int yCoord, int zCoord, int slot, int slot1) {
        this.x = xCoord;
        this.y = yCoord;
        this.z = zCoord;
        this.thisslot = slot;
        this.thatslot = slot1;
        this.isMade = true;
    }

    public CompoundTag writeToNBT(CompoundTag nbttagcompound) {
        nbttagcompound.putInt("wx", this.x);
        nbttagcompound.putInt("wy", this.y);
        nbttagcompound.putInt("wz", this.z);
        nbttagcompound.putBoolean("made", this.isMade);
        nbttagcompound.putInt("wthisslot", thisslot);
        nbttagcompound.putInt("wthatslot", thatslot);
        nbttagcompound.putInt("wexsize", xadd.size());
        nbttagcompound.putBoolean("wback", backwired);
        nbttagcompound.putFloat("wred", red);
        nbttagcompound.putFloat("wgreen", green);
        nbttagcompound.putFloat("wblue", blue);
        nbttagcompound.putFloat("walpha", alpha);
        nbttagcompound.putFloat("wwidth", width);
        for(int i = 0; i < xadd.size(); i++){
            nbttagcompound.putInt("xadd" + i, xadd.get(i));
            nbttagcompound.putInt("yadd" + i, yadd.get(i));
            nbttagcompound.putInt("zadd" + i, zadd.get(i));
            nbttagcompound.putInt("sideadd" + i, sideadd.get(i));
        }
        return nbttagcompound;
    }

    public void readFromNBT(CompoundTag nbttagcompound) {
        this.x = nbttagcompound.getInteger("wx");
        this.y = nbttagcompound.getInteger("wy");
        this.z = nbttagcompound.getInteger("wz");
        this.isMade = nbttagcompound.getBoolean("made");
        this.thisslot = nbttagcompound.getInteger("wthisslot");
        this.thatslot = nbttagcompound.getInteger("wthatslot");
        this.backwired = nbttagcompound.getBoolean("wback");
        this.red = nbttagcompound.getFloat("wred");
        this.green = nbttagcompound.getFloat("wgreen");
        this.blue = nbttagcompound.getFloat("wblue");
        this.alpha = nbttagcompound.getFloat("walpha");
        this.width = nbttagcompound.getFloat("wwidth");
        for(int i = 0; i < nbttagcompound.getInteger("wexsize"); i++){
            this.xadd.add(i, nbttagcompound.getInteger("xadd" + i));
            this.yadd.add(i, nbttagcompound.getInteger("yadd" + i));
            this.zadd.add(i, nbttagcompound.getInteger("zadd" + i));
            this.sideadd.add(i, nbttagcompound.getInteger("sideadd" + i));
        }
    }
}
