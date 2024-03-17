package net.brokenmoon.afloydwiremod.tileentity;

import com.mojang.nbt.CompoundTag;
import net.brokenmoon.afloydwiremod.WireMod;
import net.brokenmoon.afloydwiremod.api.AbstractWireTileEntity;
import net.brokenmoon.afloydwiremod.gui.WiringButton;
import net.brokenmoon.afloydwiremod.tile.RedstoneLinkTile;

public class RedstoneLinkTileEntity extends AbstractWireTileEntity {

    public boolean isActive = false;
    public boolean shouldnotremove = false;
    public RedstoneLinkTileEntity(){
        super();
        inputs = new WiringButton[1];
        outputs = new WiringButton[1];
        outputs[0] = new WiringButton(214, 240, "Output", 0);
        inputs[0] = new WiringButton(214, 220, "Input", 0);
        this.initialized = true;
    }

    @Override
    public void readFromNBT(CompoundTag nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        this.isActive = nbttagcompound.getBoolean("activity");
    }

    @Override
    public void writeToNBT(CompoundTag nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.putBoolean("activity", this.isActive);
    }
    @Override
    public void update() {
        if(inputs[0].floatvalue > 0 && !this.isActive && worldObj.blockExists(xCoord, yCoord, zCoord) && worldObj.getBlockId(xCoord, yCoord, zCoord) == WireMod.LinkTileInactive.blockID){
            this.shouldnotremove = true;
            RedstoneLinkTile.updateLinkBlockState(true, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            this.shouldnotremove = false;
            this.isActive = true;
        } else if(inputs[0].floatvalue <= 0 && this.isActive && worldObj.blockExists(xCoord, yCoord, zCoord) && worldObj.getBlockId(xCoord, yCoord, zCoord) == WireMod.LinkTileActive.blockID){
            this.shouldnotremove = true;
            RedstoneLinkTile.updateLinkBlockState(false, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            this.shouldnotremove = false;
            this.isActive = false;
        }
        worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
    }
}
