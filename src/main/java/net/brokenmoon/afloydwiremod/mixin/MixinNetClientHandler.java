package net.brokenmoon.afloydwiremod.mixin;

import net.brokenmoon.afloydwiremod.api.AbstractWireTileEntity;
import net.brokenmoon.afloydwiremod.item.ToolWiring;
import net.brokenmoon.afloydwiremod.mixinInterfaces.IEntityPlayer;
import net.brokenmoon.afloydwiremod.mixinInterfaces.INetHandler;
import net.brokenmoon.afloydwiremod.packet.WiremodPacketSyncIO;
import net.brokenmoon.afloydwiremod.packet.WiremodProgrammerGuiPacket;
import net.brokenmoon.afloydwiremod.packet.WiremodProgrammerPacket;
import net.brokenmoon.afloydwiremod.packet.WiremodSettingsPacket;
import net.brokenmoon.afloydwiremod.packet.WiremodWireGuiPacket;
import net.brokenmoon.afloydwiremod.packet.WiremodWiringGuiPacket;
import net.brokenmoon.afloydwiremod.packet.WiremodWiringPacket;
import net.brokenmoon.afloydwiremod.tileentity.ChipTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.net.handler.NetClientHandler;
import net.minecraft.core.block.entity.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = NetClientHandler.class, remap = false)
public class MixinNetClientHandler implements INetHandler {
    @Shadow
    Minecraft mc;
    @Override
    public void wiremodHandleOpenProgrammerGUI(WiremodProgrammerGuiPacket packet) {
        if(packet.mode == 0)
            ((IEntityPlayer)this.mc.thePlayer).displayGuiProgrammer((ChipTileEntity)this.mc.theWorld.getBlockTileEntity(packet.x, packet.y, packet.z));
        if(packet.mode == 1)
            ((IEntityPlayer)this.mc.thePlayer).displayGuiSettings((AbstractWireTileEntity)this.mc.theWorld.getBlockTileEntity(packet.x, packet.y, packet.z));
    }

    @Override
    public void wiremodHandleProgramTile(WiremodProgrammerPacket wiremodProgrammerPacket) {

    }

    @Override
    public void wiremodHandleOpenWiringGUI(WiremodWiringGuiPacket packet) {
        if(this.mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ToolWiring)
            ((IEntityPlayer)this.mc.thePlayer).displayGuiWiring(((ToolWiring)this.mc.thePlayer.inventory.getCurrentItem().getItem()), (AbstractWireTileEntity) this.mc.theWorld.getBlockTileEntity(packet.x, packet.y, packet.z), packet.x2, packet.y2, packet.z2);
    }

    @Override
    public void wiremodHandleIODisc(WiremodPacketSyncIO packet) {
        boolean test = this.mc.theWorld.getBlockTileEntity(packet.x, packet.y, packet.z) instanceof ChipTileEntity;
        TileEntity tileentity;
        if (this.mc.theWorld.isBlockLoaded(packet.x, packet.y, packet.z) && (tileentity = this.mc.theWorld.getBlockTileEntity(packet.x, packet.y, packet.z)) instanceof AbstractWireTileEntity) {
            AbstractWireTileEntity wireEntity = (AbstractWireTileEntity)tileentity;
            wireEntity.inputs = packet.io;
            wireEntity.outputs = packet.oi;
            wireEntity.onInventoryChanged();
            if(wireEntity instanceof ChipTileEntity){
                ((ChipTileEntity) wireEntity).mode = packet.mode;
                ((ChipTileEntity) wireEntity).tickAmount = packet.tickAmount;
            }
        }
    }

    @Override
    public void wiremodHandleWireChips(WiremodWiringPacket wiremodWiringPacket) {

    }

    @Override
    public void wiremodHandleWireToolSettingsGui(WiremodWireGuiPacket wiremodWireGuiPacket) {
        ((IEntityPlayer)this.mc.thePlayer).displayGuiWireSettings();
    }

    @Override
    public void wiremodHandleSettings(WiremodSettingsPacket wiremodSettingsPacket) {

    }
}
