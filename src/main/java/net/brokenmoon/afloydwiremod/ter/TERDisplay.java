package net.brokenmoon.afloydwiremod.ter;

import net.brokenmoon.afloydwiremod.tileentity.DisplayTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.FontRenderer;
import net.minecraft.client.render.tileentity.TileEntityRenderer;
import org.lwjgl.opengl.GL11;

public class TERDisplay extends TileEntityRenderer<DisplayTileEntity> {
    @Override
    public void doRender(DisplayTileEntity tileEntity, double d, double e, double f, float g) {
		if(tileEntity.inputs != null){
			for(int it = 0; it < tileEntity.inputs.length; it++){
				if(tileEntity.inputs[it] != null && tileEntity.isLineReversed != null){
					this.renderTextLine(d, e, f, it, tileEntity.inputs[it].floatvalue, tileEntity.inputs[it].stringvalue, tileEntity.isLineReversed[it], Minecraft.getMinecraft(this).theWorld.getBlockMetadata(tileEntity.x, tileEntity.y, tileEntity.z));
				}
			}
		}
    }

    private void renderTextLine(double d, double d1, double d2, int line, float floatvalue, String stringvalue, boolean reversed, int meta) {
        String strToRender;
        if(reversed) {
            strToRender = floatvalue + stringvalue;
        }else{
            strToRender = stringvalue + floatvalue;
        }
        FontRenderer fontRenderer = this.getFontRenderer();
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        float yoff = 0.151f;
        switch(meta) {
            case 5:
                GL11.glTranslatef(0.5f, yoff, 0.53f);
                GL11.glRotatef(-90f, 1, 0, 0);
                break;
            case 0:
                GL11.glTranslatef(0.5f, 1 - yoff, 0.47f);
                GL11.glRotatef(90f, 1, 0, 0);
                break;
            case 4:
                GL11.glTranslatef(0.5f, 0.47f, 1 - yoff);
                GL11.glRotatef(180f, 0, 1, 0);
                break;
            case 3:
                GL11.glTranslatef(0.5f, 0.47f, yoff);
                break;
            case 1:
                GL11.glRotatef(90f, 0, 1, 0);
                GL11.glTranslatef(-0.5f, 0.47f, yoff);
                break;
            case 2:
                GL11.glRotatef(-90f, 0, 1, 0);
                GL11.glTranslatef(0.5f, 0.47f, - 1 + yoff);
                break;
        }
        GL11.glScalef(0.02f, -0.02f, 0.02f);
        fontRenderer.drawString(strToRender, -fontRenderer.getStringWidth(strToRender) / 2, line * 10 - 4 * 5, 0xFFFFFF);
        fontRenderer.drawString(strToRender, -fontRenderer.getStringWidth(strToRender) / 2, line * 10 - 4 * 5, 0xFFFFFF);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
}
