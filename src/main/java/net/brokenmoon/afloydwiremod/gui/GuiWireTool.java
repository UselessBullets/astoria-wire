package net.brokenmoon.afloydwiremod.gui;

import net.brokenmoon.afloydwiremod.WireMod;
import net.brokenmoon.afloydwiremod.item.ToolWiring;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlider;

public class GuiWireTool extends GuiScreen {
    public ToolWiring tool;
    public GuiSlider red;
    public GuiSlider green;
    public GuiSlider blue;
    public GuiSlider alpha;
    public GuiSlider widthslider;
    public GuiWireTool(EntityPlayerSP entityPlayerSP) {
        super.init();
        this.tool = WireMod.ToolWiringClass;
    }

    @Override
    public void init() {
        red = new GuiSlider(1, this.width/2-100, this.height/2-50, 200, 20, "Red", tool.red);
        green = new GuiSlider(2, this.width/2-100, this.height/2-30, 200, 20, "Green", tool.green);
        blue = new GuiSlider(3, this.width/2-100, this.height/2-10, 200, 20, "Blue", tool.blue);
        alpha = new GuiSlider(4, this.width/2-100, this.height/2+10, 200, 20, "Alpha", tool.alpha);
        widthslider = new GuiSlider(5, this.width/2-100, this.height/2+30, 200, 20, "Width", tool.width);
        this.controlList.add(red);
        this.controlList.add(green);
        this.controlList.add(blue);
        this.controlList.add(alpha);
        this.controlList.add(widthslider);
    }
    @Override
    public void drawScreen(int x, int y, float renderPartialTicks) {
        super.drawScreen(x, y, renderPartialTicks);
        if (this.red.dragging) {
            this.tool.red = red.sliderValue;
        }
        if (this.green.dragging) {
            this.tool.green = green.sliderValue;
        }
        if (this.blue.dragging) {
            this.tool.blue = blue.sliderValue;
        }
        if (this.alpha.dragging) {
            this.tool.alpha = alpha.sliderValue;
        }
        if (this.widthslider.dragging) {
            this.tool.width = widthslider.sliderValue;
        }
    }

}
