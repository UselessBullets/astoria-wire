package net.brokenmoon.afloydwiremod.gui;

import net.brokenmoon.afloydwiremod.WireMod;
import net.brokenmoon.afloydwiremod.api.AbstractWireTileEntity;
import net.brokenmoon.afloydwiremod.packet.WiremodProgrammerPacket;
import net.brokenmoon.afloydwiremod.tileentity.ChipTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.net.handler.NetClientHandler;
import net.minecraft.core.entity.player.EntityPlayer;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiProgrammer extends GuiScrollable {

    private AbstractWireTileEntity wireEntity;

    public int xSize = 100;
    public int ySize = 180;
    private int numOfElements = 0;

    @Override
    public void init() {
        //this.height - 240 is top
        //This.width / 2
        numOfElements = 0;
        this.controlList.add(new GuiButtonExtended(numOfElements++, this.width, "Constant"));
        this.controlList.add(new GuiButtonExtended(numOfElements++, this.width, "Increment"));
        this.controlList.add(new GuiButtonExtended(numOfElements++, this.width, "Decrement"));
        this.controlList.add(new GuiButtonExtended(numOfElements++, this.width, "Counter"));
        this.controlList.add(new GuiButtonExtended(numOfElements++, this.width, "Duplicate"));
        this.controlList.add(new GuiButtonExtended(numOfElements++, this.width, "Addition"));
        this.controlList.add(new GuiButtonExtended(numOfElements++, this.width, "Subtraction"));
        this.controlList.add(new GuiButtonExtended(numOfElements++, this.width, "Multiplication"));
        this.controlList.add(new GuiButtonExtended(numOfElements++, this.width, "Division"));
        this.controlList.add(new GuiButtonExtended(numOfElements++, this.width, "Memory"));
        this.controlList.add(new GuiButtonExtended(numOfElements++, this.width, "Pulse"));
        this.controlList.add(new GuiButtonExtended(numOfElements++, this.width, "TRUE"));
        this.controlList.add(new GuiButtonExtended(numOfElements++, this.width, "FALSE"));
        this.controlList.add(new GuiButtonExtended(numOfElements++, this.width, "AND"));
        this.controlList.add(new GuiButtonExtended(numOfElements++, this.width, "NAND"));
        this.controlList.add(new GuiButtonExtended(numOfElements++, this.width, "OR"));
        this.controlList.add(new GuiButtonExtended(numOfElements++, this.width, "NOR"));
        this.controlList.add(new GuiButtonExtended(numOfElements++, this.width, "XOR"));
        this.controlList.add(new GuiButtonExtended(numOfElements++, this.width, "XNOR"));
        this.controlList.add(new GuiButtonExtended(numOfElements++, this.width, "NOT"));
        this.controlList.add(new GuiButtonExtended(numOfElements++, this.width, "=="));
        this.controlList.add(new GuiButtonExtended(numOfElements++, this.width, "!="));
        this.controlList.add(new GuiButtonExtended(numOfElements++, this.width, ">"));
        this.controlList.add(new GuiButtonExtended(numOfElements++, this.width, ">="));
        this.controlList.add(new GuiButtonExtended(numOfElements++, this.width, "<"));
        this.controlList.add(new GuiButtonExtended(numOfElements++, this.width, "<="));
        this.controlList.add(new GuiButtonExtended(numOfElements++, this.width, "<=>"));
    }

    public GuiProgrammer(EntityPlayer player, ChipTileEntity wireEntity) {
        super.init();
        this.player = player;
        this.wireEntity = wireEntity;
    }

    @Override
    protected void buttonPressed(GuiButton guibutton) {
            switch(guibutton.displayString){
                case "Constant":
                    this.setMode("constant");
                    break;
                case "Increment":
                    this.setMode("inc");
                    break;
                case "Duplicate":
                    this.setMode("dup");
                    break;
                case "Addition":
                    this.setMode("add");
                    break;
                case "Subtraction":
                    this.setMode("sub");
                    break;
                case "Multiplication":
                    this.setMode("mult");
                    break;
                case "Division":
                    this.setMode("div");
                    break;
                case "Decrement":
                    this.setMode("dec");
                    break;
                case "Counter":
                    this.setMode("incdec");
                    break;
                case "Memory":
                    this.setMode("mem");
                    break;
                case "TRUE":
                case "FALSE":
                case "NOT":
                case "AND":
                case "OR":
                case "XOR":
                case "NAND":
                case "NOR":
                case "XNOR":
                case "==":
                case "!=":
                case ">":
                case ">=":
                case "<":
                case "<=":
                case "<=>":
                case "Pulse":
                    this.setMode(guibutton.displayString);
                    break;
        }
        this.mc.displayGuiScreen(null);
    }

    public void setMode(String string){
        if(this.player instanceof EntityClientPlayerMP){
            NetClientHandler netclienthandler = ((EntityClientPlayerMP)this.mc.thePlayer).sendQueue;
            netclienthandler.addToSendQueue(new WiremodProgrammerPacket(string, wireEntity.x, wireEntity.y, wireEntity.z));
            return;
        }
        ((ChipTileEntity)Minecraft.getMinecraft(this).theWorld.getBlockTileEntity(wireEntity.x, wireEntity.y, wireEntity.z)).setMode(string);

    }

    @Override
    public void drawScreen(int x, int y, float renderPartialTicks){
        this.drawDefaultBackground();
        //Draw the overlay
        this.drawGuiBackground(renderPartialTicks);
        GL11.glPushMatrix();
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor((width / 2 - 60) * this.mc.resolution.scale, (height / 2 - 80) * this.mc.resolution.scale, 120 * this.mc.resolution.scale, 160 * this.mc.resolution.scale);
        int k = (this.height - this.ySize) / 2;
        int wheel = Mouse.getDWheel();
        if(wheel > 0)
            scroll += WireMod.config.scrollrate;
        if (wheel < 0)
            scroll -= WireMod.config.scrollrate;
        if(scroll > 0)
            scroll = 0;
        if(-scroll > (numOfElements - 16) * GuiButtonExtended.height)
            scroll = -((numOfElements - 16) * GuiButtonExtended.height);
        for (int i = 0; i < this.controlList.size(); ++i) {
            GuiButtonExtended guibutton = (GuiButtonExtended)this.controlList.get(i);
            guibutton.yPosition = k + (guibutton.height * (guibutton.id + 1) + scroll);
            guibutton.drawButton(this.mc, x, y);
        }
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    protected void drawGuiBackground(float f) {
        int guiTexture = this.mc.renderEngine.getTexture("/assets/afloydwiremod/gui/programmer.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(guiTexture);
        int j = (this.width - this.xSize) / 2;
        int k = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(j, k, 0, 0, this.xSize, this.ySize);
    }
}
