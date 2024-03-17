package net.brokenmoon.afloydwiremod.tileentity;

import com.mojang.nbt.CompoundTag;
import net.brokenmoon.afloydwiremod.api.AbstractWireTileEntity;
import net.brokenmoon.afloydwiremod.gui.WiringButton;

public class DisplayTileEntity extends AbstractWireTileEntity {
    public boolean[] isLineReversed = new boolean[4];
    public DisplayTileEntity(){
        super();
        inputs = new WiringButton[4];
        inputs[0] = new WiringButton(214, 220, "Line 1", 0);
        inputs[1] = new WiringButton(214, 220, "Line 2", 1);
        inputs[2] = new WiringButton(214, 220, "Line 3", 2);
        inputs[3] = new WiringButton(214, 220, "Line 4", 3);
        outputs = new WiringButton[0];
        this.initialized = true;
    }

    @Override
    public void readFromNBT(CompoundTag nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        for(int i = 0; i < isLineReversed.length; i++){
            isLineReversed[i] = nbttagcompound.getBoolean("reverse" + i);
        }
    }

    @Override
    public void writeToNBT(CompoundTag nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        for(int i = 0; i < isLineReversed.length; i++){
            nbttagcompound.putBoolean("reverse" + i, isLineReversed[i]);
        }
    }
}
