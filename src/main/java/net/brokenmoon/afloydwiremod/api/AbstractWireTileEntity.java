package net.brokenmoon.afloydwiremod.api;

import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.ListTag;
import net.brokenmoon.afloydwiremod.gui.WiringButton;
import net.brokenmoon.afloydwiremod.packet.WiremodPacketSyncIO;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.net.packet.Packet;

public abstract class AbstractWireTileEntity extends TileEntity {
    public WiringButton[] inputs = null;
    public WiringButton[] outputs = null;
    public boolean initialized = false;
    public boolean hasSettings = false;

    public void update() {
        updateIO();
        worldObj.markBlockNeedsUpdate(x, y, z);
    }

    @Override
    public void readFromNBT(CompoundTag nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        this.initialized = nbttagcompound.getBoolean("init");
        this.hasSettings = nbttagcompound.getBoolean("set");
        if(nbttagcompound.getBoolean("inputExists")){
            this.inputs = new WiringButton[nbttagcompound.getInteger("inputLength")];
            ListTag nbttaglistInputs = nbttagcompound.getList("Inputs");
            for (int i = 0; i < nbttaglistInputs.tagCount(); ++i) {
				CompoundTag nbttagcompound1 = (CompoundTag)nbttaglistInputs.tagAt(i);
                this.inputs[i] = new WiringButton();
                this.inputs[i].readFromNBT(nbttagcompound1);
            }
        }
        if(nbttagcompound.getBoolean("outputExists")){
            this.outputs = new WiringButton[nbttagcompound.getInteger("outputLength")];
            ListTag nbttaglistOutputs = nbttagcompound.getList("Outputs");
            for (int i = 0; i < nbttaglistOutputs.tagCount(); ++i) {
				CompoundTag nbttagcompound1 = (CompoundTag)nbttaglistOutputs.tagAt(i);
                this.outputs[i] = new WiringButton();
                this.outputs[i].readFromNBT(nbttagcompound1);
            }
        }
    }

    @Override
    public void writeToNBT(CompoundTag nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.putBoolean("init", this.initialized);
        nbttagcompound.putBoolean("set", this.hasSettings);
        if(inputs != null){
            nbttagcompound.putBoolean("inputExists", true);
            nbttagcompound.putInt("inputLength", inputs.length);
            ListTag nbttaglistInputs = new ListTag();
            for (WiringButton input : this.inputs) {
                CompoundTag nbttagInput = new CompoundTag();
                input.writeToNBT(nbttagInput);
                nbttaglistInputs.addTag(nbttagInput);
            }
            nbttagcompound.putList("Inputs", nbttaglistInputs);
        } else {
            nbttagcompound.putBoolean("inputExists", false);
        }
        if(outputs != null){
            nbttagcompound.putBoolean("outputExists", true);
            nbttagcompound.putInt("outputLength", outputs.length);
            ListTag nbttaglistOutputs = new ListTag();
            for (WiringButton output : this.outputs) {
                CompoundTag nbttagOutput = new CompoundTag();
                output.writeToNBT(nbttagOutput);
                nbttaglistOutputs.addTag(nbttagOutput);
            }
            nbttagcompound.putList("Outputs", nbttaglistOutputs);
        } else {
            nbttagcompound.putBoolean("outputExists", false);
        }
    }

    public void updateIO(){
        if(outputs != null) {
            for (int i = 0; i < outputs.length; ++i) {
                if (outputs[i].wire != null && outputs[i].wire.thisslot > -1) {
                    WireConnection wire = outputs[i].wire;
                    Boolean doUpdate = false;
                    AbstractWireTileEntity otherChip = (AbstractWireTileEntity)this.worldObj.getBlockTileEntity(wire.x, wire.y, wire.z);
                    if(otherChip == null || otherChip.outputs == null){
                        this.outputs[i].wire = new WireConnection();
                    } else {
                        if (outputs[i].floatvalue != otherChip.inputs[wire.thatslot].floatvalue) {
                            otherChip.inputs[wire.thatslot].floatvalue = outputs[i].floatvalue;
                            doUpdate = true;
                        }
                        if (!outputs[i].stringvalue.equals(otherChip.inputs[wire.thatslot].stringvalue)) {
                            otherChip.inputs[wire.thatslot].stringvalue = outputs[i].stringvalue;
                            doUpdate = true;
                        }
                        if (doUpdate)
                            otherChip.update();
                    }
                }
            }
        }
        worldObj.markBlockNeedsUpdate(x, y, z);
    }

    public void prepForDelete() {
        if(outputs != null) {
            for (int i = 0; i < outputs.length; ++i) {
                if (outputs[i].wire != null) {
                    WireConnection wire = outputs[i].wire;
                    AbstractWireTileEntity otherChip = (AbstractWireTileEntity)this.worldObj.getBlockTileEntity(wire.x, wire.y, wire.z);
                    if(otherChip != null) {
                        outputs[i].floatvalue = 0;
                        outputs[i].stringvalue = "";
                        outputs[i].wire.isMade = false;
                        otherChip.inputs[wire.thatslot].wire = new WireConnection();
                        this.updateIO();
                        otherChip.updateIO();
                    }
                    worldObj.markBlockNeedsUpdate(wire.x, wire.y, wire.z);
                }
            }
        }
        if(inputs != null) {
            for (int i = 0; i < inputs.length; ++i) {
                if (inputs[i].wire != null) {
                    WireConnection wire = inputs[i].wire;
                    AbstractWireTileEntity otherChip = (AbstractWireTileEntity)this.worldObj.getBlockTileEntity(wire.x, wire.y, wire.z);
                    if(otherChip != null) {
                        otherChip.outputs[wire.thatslot].wire = new WireConnection();
                        otherChip.updateIO();
                    }
                    worldObj.markBlockNeedsUpdate(wire.x, wire.y, wire.z);
                }
            }
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        return new WiremodPacketSyncIO(this.x, this.y, this.z, inputs, outputs);
    }
}
