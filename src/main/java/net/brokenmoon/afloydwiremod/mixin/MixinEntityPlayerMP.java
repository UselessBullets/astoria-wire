package net.brokenmoon.afloydwiremod.mixin;

import net.brokenmoon.afloydwiremod.api.AbstractWireTileEntity;
import net.brokenmoon.afloydwiremod.item.ToolWiring;
import net.brokenmoon.afloydwiremod.mixinInterfaces.IEntityPlayer;
import net.brokenmoon.afloydwiremod.packet.WiremodProgrammerGuiPacket;
import net.brokenmoon.afloydwiremod.packet.WiremodWireGuiPacket;
import net.brokenmoon.afloydwiremod.packet.WiremodWiringGuiPacket;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.net.handler.NetServerHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = EntityPlayerMP.class, remap = false)
public class MixinEntityPlayerMP implements IEntityPlayer {
    @Shadow public NetServerHandler playerNetServerHandler;

    @Override
    public void displayGuiProgrammer(AbstractWireTileEntity chip) {
        this.playerNetServerHandler.sendPacket(new WiremodProgrammerGuiPacket(0, chip.x, chip.y, chip.z));
    }

    @Override
    public void displayGuiWiring(ToolWiring tool, AbstractWireTileEntity chip, int x, int y, int z) {
        this.playerNetServerHandler.sendPacket(new WiremodWiringGuiPacket(chip.x, chip.y, chip.z, x, y, z));

    }

    @Override
    public void displayGuiSettings(AbstractWireTileEntity chip) {
        this.playerNetServerHandler.sendPacket(new WiremodProgrammerGuiPacket(1, chip.x, chip.y, chip.z));
    }

    @Override
    public void displayGuiWireSettings() {
        this.playerNetServerHandler.sendPacket(new WiremodWireGuiPacket());
    }
}
