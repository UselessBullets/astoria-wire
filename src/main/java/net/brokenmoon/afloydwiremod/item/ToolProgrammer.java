package net.brokenmoon.afloydwiremod.item;

import net.minecraft.core.item.Item;
public class ToolProgrammer extends Item {
    public ToolProgrammer(int i) {
        super(i);
        this.maxStackSize = 1;
    }

    @Override
    public boolean isFull3D() {
        return true;
    }
}
