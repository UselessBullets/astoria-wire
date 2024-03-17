package net.brokenmoon.afloydwiremod.mixin;

import net.brokenmoon.afloydwiremod.api.AbstractWireTileEntity;
import net.brokenmoon.afloydwiremod.gui.GuiProgrammer;
import net.brokenmoon.afloydwiremod.gui.GuiSettings;
import net.brokenmoon.afloydwiremod.gui.GuiWireTool;
import net.brokenmoon.afloydwiremod.gui.GuiWiring;
import net.brokenmoon.afloydwiremod.item.ToolWiring;
import net.brokenmoon.afloydwiremod.mixinInterfaces.IEntityPlayer;
import net.brokenmoon.afloydwiremod.tileentity.ChipTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = EntityPlayerSP.class, remap = false)
public class MixinEntityPlayerSP implements IEntityPlayer {
    @Shadow
    protected Minecraft mc;

    @Override
    public void displayGuiProgrammer(AbstractWireTileEntity chip) {
        this.mc.displayGuiScreen(new GuiProgrammer(((EntityPlayerSP)(Object)this), (ChipTileEntity) chip));
    }

    @Override
    public void displayGuiWiring(ToolWiring tool, AbstractWireTileEntity chip, int x, int y, int z) {
        this.mc.displayGuiScreen(new GuiWiring(((EntityPlayerSP)(Object)this), tool, chip, x, y, z));
    }

    @Override
    public void displayGuiSettings(AbstractWireTileEntity chip) {
        this.mc.displayGuiScreen(new GuiSettings(((EntityPlayerSP)(Object)this), chip));
    }

    @Override
    public void displayGuiWireSettings() {
        this.mc.displayGuiScreen(new GuiWireTool(((EntityPlayerSP)(Object)this)));
    }
}
