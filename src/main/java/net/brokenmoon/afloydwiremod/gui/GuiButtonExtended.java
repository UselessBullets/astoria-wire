package net.brokenmoon.afloydwiremod.gui;

import net.minecraft.client.gui.GuiButton;

public class GuiButtonExtended extends GuiButton {

    public String type;
    public int slot;
    public static final int width = 80;
    public static final int height = 10;
    public GuiButtonExtended(int id, int xPosition, String s, String extra, int slot) {
        super(id, xPosition / 2 - (width / 2), 50 - height + (height * (id + 1)), width, height, s);
        this.type = extra;
        this.slot = slot;
    }
    public GuiButtonExtended(int id, int xPosition, String s) {
        super(id, xPosition / 2 - (width / 2), 50 - height + (height * (id + 1)), width, height, s);
    }
}
