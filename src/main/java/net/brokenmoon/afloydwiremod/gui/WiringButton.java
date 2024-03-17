package net.brokenmoon.afloydwiremod.gui;
import com.mojang.nbt.CompoundTag;
import net.brokenmoon.afloydwiremod.api.WireConnection;

public class WiringButton {
    public int x;
    public int y;
    public String buttonString;
    public int slot;
    public float floatvalue;
    public String stringvalue = "";
    public WireConnection wire = new WireConnection();
    public WiringButton(){

    }
    public WiringButton(int x, int y, String buttonString, int slot){
        this.x = x;
        this.y = y;
        this.buttonString = buttonString;
        this.slot = slot;
    }

    public CompoundTag writeToNBT(CompoundTag nbttagcompound) {
        nbttagcompound.putInt("bx", this.x);
        nbttagcompound.putInt("by", this.y);
        nbttagcompound.putString("bbuttonString", buttonString);
        nbttagcompound.putInt("bslot", slot);
        nbttagcompound.putFloat("bfloatvalue", floatvalue);
        nbttagcompound.putString("bstringvalue", stringvalue);
        wire.writeToNBT(nbttagcompound);
        return nbttagcompound;
    }

    public void readFromNBT(CompoundTag nbttagcompound) {
        this.x = nbttagcompound.getInteger("bx");
        this.y = nbttagcompound.getInteger("by");
        this.buttonString = nbttagcompound.getString("bbuttonString");
        this.slot = nbttagcompound.getInteger("bslot");
        this.floatvalue = nbttagcompound.getFloat("bfloatvalue");
        this.stringvalue = nbttagcompound.getString("bstringvalue");
        wire.readFromNBT(nbttagcompound);
    }
}
