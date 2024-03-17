package net.brokenmoon.afloydwiremod.mixinInterfaces;

import net.brokenmoon.afloydwiremod.api.AbstractWireTileEntity;
import net.brokenmoon.afloydwiremod.item.ToolWiring;
import net.brokenmoon.afloydwiremod.tileentity.ChipTileEntity;

public interface IEntityPlayer {
    public void displayGuiProgrammer(AbstractWireTileEntity chip);

    public void displayGuiWiring(ToolWiring tool, AbstractWireTileEntity chip, int x, int y, int z);
    public void displayGuiSettings(AbstractWireTileEntity chip);
    public void displayGuiWireSettings();
}
