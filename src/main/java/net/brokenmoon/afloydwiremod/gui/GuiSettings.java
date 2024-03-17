package net.brokenmoon.afloydwiremod.gui;

import net.brokenmoon.afloydwiremod.api.AbstractWireTileEntity;
import net.brokenmoon.afloydwiremod.packet.WiremodSettingsPacket;
import net.brokenmoon.afloydwiremod.tileentity.ChipTileEntity;
import net.minecraft.client.entity.player.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.net.handler.NetClientHandler;
import net.minecraft.core.entity.player.EntityPlayer;

public class GuiSettings extends GuiScreen {
    private EntityPlayer player;
    private AbstractWireTileEntity wireEntity;
    @Override
    public void initGui() {
        //this.height - 240 is top
        //This.width / 2 - 214 is left
        //Inputs
        if(wireEntity instanceof ChipTileEntity){
            switch(((ChipTileEntity) wireEntity).mode){
                case "constant":
                    initConst();
                    break;
                case "Pulse":
                    initPulse();
                    break;
            }
        }

    }

    private void initPulse() {
        this.controlList.add(new GuiButton(1, this.width /2 - 100, this.height/2 - 20 - 10, "+"));
        this.controlList.add(1,new GuiButton(0, this.width /2 - 100, this.height/2 - 10, ((ChipTileEntity)wireEntity).tickAmount + ""));
        this.controlList.add(new GuiButton(2, this.width /2 - 100, this.height/2 + 20 - 10, "-"));
    }

    public void initConst(){
        this.controlList.add(new GuiButton(1, this.width /2 - 100, this.height/2 - 20 - 10, "+"));
        this.controlList.add(1,new GuiButton(0, this.width /2 - 100, this.height/2 - 10 , wireEntity.outputs[0].floatvalue + ""));
        this.controlList.add(new GuiButton(2, this.width /2 - 100, this.height/2 + 20 - 10, "-"));
    }

    public GuiSettings(EntityPlayer player, AbstractWireTileEntity wireEntity) {
        super.init();
        this.player = player;
        this.wireEntity = wireEntity;
    }

    @Override
    protected void buttonPressed(GuiButton guiButton){
        if(wireEntity instanceof ChipTileEntity){
            switch(((ChipTileEntity)wireEntity).mode){
                case "constant":
                    constAction(guiButton);
                    break;
                case "Pulse":
                    pulseAction(guiButton);
                    break;
            }
        }
    }

    private void pulseAction(GuiButton guiButton) {
        if(guiButton.id == 1){
            ((ChipTileEntity)wireEntity).tickAmount++;
        }
        if(guiButton.id == 2 && ((ChipTileEntity)wireEntity).tickAmount > 0){
            ((ChipTileEntity)wireEntity).tickAmount--;
        }
        if(this.player instanceof EntityClientPlayerMP){
            NetClientHandler netclienthandler = ((EntityClientPlayerMP)this.mc.thePlayer).sendQueue;
            netclienthandler.addToSendQueue(new WiremodSettingsPacket(1, ((ChipTileEntity)wireEntity).tickAmount, wireEntity.x, wireEntity.y, wireEntity.z));
        }
        this.controlList.get(1).displayString = ((ChipTileEntity)wireEntity).tickAmount + "";
    }

    private void constAction(GuiButton guiButton) {
        if(guiButton.id == 1){
            wireEntity.outputs[0].floatvalue++;
        }
        if(guiButton.id == 2){
            wireEntity.outputs[0].floatvalue--;
        }
        this.controlList.get(1).displayString = wireEntity.outputs[0].floatvalue + "";
        if(this.player instanceof EntityClientPlayerMP){
            NetClientHandler netclienthandler = ((EntityClientPlayerMP)this.mc.thePlayer).sendQueue;
            netclienthandler.addToSendQueue(new WiremodSettingsPacket(0, wireEntity.outputs[0].floatvalue, wireEntity.x, wireEntity.y, wireEntity.z));
        }
        wireEntity.updateIO();
    }
}
