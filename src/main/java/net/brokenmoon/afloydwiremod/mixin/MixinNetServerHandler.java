package net.brokenmoon.afloydwiremod.mixin;

import net.brokenmoon.afloydwiremod.api.AbstractWireTileEntity;
import net.brokenmoon.afloydwiremod.api.WireConnection;
import net.brokenmoon.afloydwiremod.mixinInterfaces.INetHandler;
import net.brokenmoon.afloydwiremod.packet.*;
import net.brokenmoon.afloydwiremod.tileentity.ChipTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.net.handler.NetServerHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = NetServerHandler.class, remap = false)
public class MixinNetServerHandler implements INetHandler {
    @Shadow
    private MinecraftServer mcServer;
    @Shadow
    private EntityPlayerMP playerEntity;

    @Override
    public void wiremodHandleOpenProgrammerGUI(WiremodProgrammerGuiPacket packet) {

    }

    @Override
    public void wiremodHandleProgramTile(WiremodProgrammerPacket packet) {
        ((ChipTileEntity)this.mcServer.getDimensionWorld(this.playerEntity.dimension).getBlockTileEntity(packet.x, packet.y, packet.z)).setMode(packet.mode);
    }

    @Override
    public void wiremodHandleOpenWiringGUI(WiremodWiringGuiPacket wiremodWiringGuiPacket) {

    }

    @Override
    public void wiremodHandleIODisc(WiremodPacketSyncIO wiremodPacketSyncIO) {

    }

    @Override
    public void wiremodHandleWireChips(WiremodWiringPacket packet) {
        switch(packet.type){
            case 0:
                AbstractWireTileEntity wireEntity;
                if(!packet.backwired) {
                    wireEntity = (AbstractWireTileEntity) this.mcServer.getDimensionWorld(this.playerEntity.dimension).getBlockTileEntity(packet.x1, packet.y1, packet.z1);
                    wireEntity.outputs[packet.slot1].wire = new WireConnection(packet.x2, packet.y2, packet.z2, packet.slot1, packet.slot2, packet.xadd, packet.yadd, packet.zadd, packet.sideadd, packet.backwired, packet.red, packet.green, packet.blue, packet.alpha, packet.width);
                } else{
                    wireEntity = (AbstractWireTileEntity) this.mcServer.getDimensionWorld(this.playerEntity.dimension).getBlockTileEntity(packet.x2, packet.y2, packet.z2);
                    wireEntity.outputs[packet.slot2].wire = new WireConnection(packet.x1, packet.y1, packet.z1, packet.slot2, packet.slot1, packet.xadd, packet.yadd, packet.zadd, packet.sideadd, packet.backwired, packet.red, packet.green, packet.blue, packet.alpha, packet.width);
                }
                wireEntity.update();
                wireEntity.updateIO();
                break;
            case 1:
                AbstractWireTileEntity otherEntity = (AbstractWireTileEntity)this.mcServer.getDimensionWorld(this.playerEntity.dimension).getBlockTileEntity(packet.x1, packet.y1, packet.z1);
                otherEntity.inputs[packet.slot2].wire = new WireConnection(packet.x2, packet.y2, packet.z2, packet.slot2, packet.slot1);
                otherEntity.update();
                otherEntity.updateIO();
                break;
        }
        this.mcServer.getDimensionWorld(this.playerEntity.dimension).markBlockNeedsUpdate(packet.x1, packet.y1, packet.z1);
        this.mcServer.getDimensionWorld(this.playerEntity.dimension).markBlockNeedsUpdate(packet.x2, packet.y2, packet.z2);
    }

    @Override
    public void wiremodHandleWireToolSettingsGui(WiremodWireGuiPacket wiremodWireGuiPacket) {

    }

    @Override
    public void wiremodHandleSettings(WiremodSettingsPacket packet) {
        AbstractWireTileEntity wireEntity = (AbstractWireTileEntity) this.mcServer.getDimensionWorld(this.playerEntity.dimension).getBlockTileEntity(packet.x, packet.y, packet.z);
        switch(packet.mode){
            case 0:
                wireEntity.outputs[0].floatvalue = packet.value;
                wireEntity.update();
                break;
            case 1:
                ((ChipTileEntity)wireEntity).tickAmount = (int)packet.value;
                wireEntity.update();
                break;
        }
    }
}
