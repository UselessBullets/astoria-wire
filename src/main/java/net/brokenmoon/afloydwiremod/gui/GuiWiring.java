package net.brokenmoon.afloydwiremod.gui;

import net.brokenmoon.afloydwiremod.api.AbstractWireTileEntity;
import net.brokenmoon.afloydwiremod.api.WireConnection;
import net.brokenmoon.afloydwiremod.item.ToolWiring;
import net.brokenmoon.afloydwiremod.packet.WiremodWiringPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.net.handler.NetClientHandler;
import net.minecraft.core.entity.player.EntityPlayer;

import java.util.ArrayList;

public class GuiWiring extends GuiScrollable {
    private ToolWiring tool;

    private int x;
    private int y;
    private int z;
    private AbstractWireTileEntity wireEntity;
    private AbstractWireTileEntity otherEntity;
    @Override
    public void init() {
        //this.height - 240 is top
        //This.width / 2 - 214 is left
        //Inputs
        if(wireEntity.inputs != null && !this.tool.type.equals("input")) {
            for (int i = 0; i < wireEntity.inputs.length; i++) {
                this.controlList.add(new GuiButtonExtended(i, this.width - GuiButtonExtended.width, wireEntity.inputs[i].buttonString, "Input", wireEntity.inputs[i].slot));
            }
        }
        //Outputs
        if(wireEntity.outputs != null && !this.tool.type.equals("output")) {
            for (int i = 0; i < wireEntity.outputs.length; i++) {
                this.controlList.add(new GuiButtonExtended(i, this.width + GuiButtonExtended.width, wireEntity.outputs[i].buttonString, "Output", wireEntity.outputs[i].slot));
            }
        }
    }

    public GuiWiring(EntityPlayer player, ToolWiring tool, AbstractWireTileEntity wireEntity, int x, int y, int z) {
        super.init();
        this.player = player;
        this.tool = tool;
        this.x = x;
        this.y = y;
        this.z = z;
        this.wireEntity = wireEntity;
        otherEntity = (AbstractWireTileEntity)Minecraft.getMinecraft(this).theWorld.getBlockTileEntity(tool.x, tool.y, tool.z);
    }

    @Override
    protected void buttonPressed(GuiButton guiButton){
        this.buttonPressed((GuiButtonExtended)guiButton);
    }
    protected void buttonPressed(GuiButtonExtended guibutton) {
        if(this.tool.type.equals("unpaired")) {
            if (guibutton.type.equals("Input")) {
                this.tool.type = "input";
                this.tool.slot = guibutton.slot;
                this.tool.x = x;
                this.tool.y = y;
                this.tool.z = z;
                tool.xadd = new ArrayList<Integer>();
                tool.yadd = new ArrayList<Integer>();
                tool.zadd = new ArrayList<Integer>();
                tool.sideadd = new ArrayList<Integer>();
            } else if (guibutton.type.equals("Output")) {
                this.tool.type = "output";
                this.tool.slot = guibutton.slot;
                this.tool.x = x;
                this.tool.y = y;
                this.tool.z = z;
                tool.xadd = new ArrayList<Integer>();
                tool.yadd = new ArrayList<Integer>();
                tool.zadd = new ArrayList<Integer>();
                tool.sideadd = new ArrayList<Integer>();
            }
        } else if(!this.tool.type.equals("unpaired")) {
            if(this.tool.type.equals("input") && wireEntity.outputs[guibutton.slot].wire.isMade != true && otherEntity != null && otherEntity.inputs[tool.slot].wire.isMade != true) {
                if(this.player instanceof EntityClientPlayerMP){
                    NetClientHandler netclienthandler = ((EntityClientPlayerMP)this.mc.thePlayer).sendQueue;
                    netclienthandler.addToSendQueue(new WiremodWiringPacket(wireEntity.x, wireEntity.y, wireEntity.z, this.tool.x, this.tool.y, this.tool.z, guibutton.slot, tool.slot, tool.xadd, tool.yadd, tool.zadd, tool.sideadd, false, tool.red, tool.green, tool.blue, tool.alpha, tool.width));
                    netclienthandler.addToSendQueue(new WiremodWiringPacket(this.tool.x, this.tool.y, this.tool.z, this.wireEntity.x, this.wireEntity.y, this.wireEntity.z, guibutton.slot, tool.slot));
                } else{
                    wireEntity.outputs[guibutton.slot].wire = new WireConnection(this.tool.x, this.tool.y, this.tool.z, guibutton.slot, tool.slot, tool.xadd, tool.yadd, tool.zadd, tool.sideadd, false, tool.red, tool.green, tool.blue, tool.alpha, tool.width);
                    otherEntity.inputs[tool.slot].wire = new WireConnection(this.wireEntity.x, this.wireEntity.y, this.wireEntity.z, tool.slot, guibutton.slot);
                    wireEntity.update();
                    wireEntity.updateIO();
                    otherEntity.update();
                    otherEntity.updateIO();
                }
                tool.xadd = new ArrayList<Integer>();
                tool.yadd = new ArrayList<Integer>();
                tool.zadd = new ArrayList<Integer>();
                tool.sideadd = new ArrayList<Integer>();
                tool.type = "unpaired";
            } else if(this.tool.type.equals("output") && otherEntity.outputs[tool.slot].wire.isMade != true && otherEntity != null && wireEntity.inputs[guibutton.slot].wire.isMade != true ) {
                if(otherEntity != null) {
                    if(this.player instanceof EntityClientPlayerMP){
                        NetClientHandler netclienthandler = ((EntityClientPlayerMP)this.mc.thePlayer).sendQueue;
                        netclienthandler.addToSendQueue(new WiremodWiringPacket(wireEntity.x, wireEntity.y, wireEntity.z, this.tool.x, this.tool.y, this.tool.z, guibutton.slot, tool.slot, tool.xadd, tool.yadd, tool.zadd, tool.sideadd, true, tool.red, tool.green, tool.blue, tool.alpha, tool.width));
                        netclienthandler.addToSendQueue(new WiremodWiringPacket(this.wireEntity.x, this.wireEntity.y, this.wireEntity.z, this.tool.x, this.tool.y, this.tool.z, tool.slot, guibutton.slot));
                    } else{
                        otherEntity.outputs[tool.slot].wire = new WireConnection(this.wireEntity.x, this.wireEntity.y, this.wireEntity.z, tool.slot, guibutton.slot, tool.xadd, tool.yadd, tool.zadd, tool.sideadd, true, tool.red, tool.green, tool.blue, tool.alpha, tool.width);
                        wireEntity.inputs[guibutton.slot].wire = new WireConnection(this.tool.x, this.tool.y, this.tool.z, guibutton.slot, tool.slot);
                        wireEntity.update();
                        wireEntity.updateIO();
                        otherEntity.update();
                        otherEntity.updateIO();
                    }

                }
                tool.xadd = new ArrayList<Integer>();
                tool.yadd = new ArrayList<Integer>();
                tool.zadd = new ArrayList<Integer>();
                tool.sideadd = new ArrayList<Integer>();
                tool.type = "unpaired";
            } else{
                tool.xadd = new ArrayList<Integer>();
                tool.yadd = new ArrayList<Integer>();
                tool.zadd = new ArrayList<Integer>();
                tool.sideadd = new ArrayList<Integer>();
                tool.type = "unpaired";
            }
        }
        this.mc.displayGuiScreen(null);
    }
}
