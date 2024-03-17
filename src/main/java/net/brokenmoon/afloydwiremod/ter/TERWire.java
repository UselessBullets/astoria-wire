package net.brokenmoon.afloydwiremod.ter;

import net.brokenmoon.afloydwiremod.api.AbstractWireTileEntity;
import net.brokenmoon.afloydwiremod.api.AbstractWireTileSided;
import net.brokenmoon.afloydwiremod.tileentity.DisplayTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.tileentity.TileEntityRenderer;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class TERWire extends TileEntityRenderer<AbstractWireTileEntity> {
    @Override
    public void doRender(AbstractWireTileEntity wireEntity, double x, double y, double z, float g) {
		if(wireEntity.outputs != null){
			for(int it = 0; it < wireEntity.outputs.length; it++){
				if(wireEntity.outputs[it] != null && wireEntity.outputs[it].wire != null && wireEntity.outputs[it].wire.isMade && Minecraft.getMinecraft(this).theWorld.getBlockTileEntity(wireEntity.outputs[it].wire.x, wireEntity.outputs[it].wire.y, wireEntity.outputs[it].wire.z) instanceof AbstractWireTileEntity){
					this.renderLineBetweenTwoPoints(
						wireEntity.x, wireEntity.y, wireEntity.z,
						wireEntity.outputs[it].wire.x, wireEntity.outputs[it].wire.y, wireEntity.outputs[it].wire.z,
						wireEntity.outputs[it].wire.red, wireEntity.outputs[it].wire.green, wireEntity.outputs[it].wire.blue, wireEntity.outputs[it].wire.alpha, wireEntity.outputs[it].wire.width * 10,
						wireEntity.outputs[it].wire.xadd, wireEntity.outputs[it].wire.yadd, wireEntity.outputs[it].wire.zadd, wireEntity.outputs[it].wire.sideadd,
						wireEntity.outputs[it].wire.backwired,
						Block.getBlock(Minecraft.getMinecraft(this).theWorld.getBlockId(wireEntity.x, wireEntity.y, wireEntity.z)) instanceof AbstractWireTileSided,
						Block.getBlock(Minecraft.getMinecraft(this).theWorld.getBlockId(wireEntity.outputs[it].wire.x, wireEntity.outputs[it].wire.y, wireEntity.outputs[it].wire.z)) instanceof AbstractWireTileSided, x, y, z);
				}
			}
		}
    }

    public void renderWireAt(AbstractWireTileEntity wireEntity, double x, double y, double z, float weird) {

    }
    public void renderLineBetweenTwoPoints(int x1, int y1, int z1, long x2, long y2, long z2, float red, float green, float blue, float alpha, float width, ArrayList<Integer> xadd, ArrayList<Integer> yadd, ArrayList<Integer> zadd, ArrayList<Integer> sideadd, boolean backwired, boolean firstblocksided, boolean secondblocksided, double x, double y, double z){
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glLineWidth(width);
        GL11.glTranslated(x, y, z);
        GL11.glDepthMask(false);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        //First
        if(!backwired) {
            if(firstblocksided){
                float xoffset = 0;
                float yoffset = 0;
                float zoffset = 0;
                switch(Minecraft.getMinecraft(this).theWorld.getBlockMetadata(x1, y1, z1)){
                    case 0:
                        xoffset = 0.5f;
                        yoffset = 0.9f;
                        zoffset = 0.5f;
                        break;
                    case 1:
                        xoffset = 0.1f;
                        yoffset = 0.5f;
                        zoffset = 0.5f;
                        break;
                    case 2:
                        xoffset = 0.9f;
                        yoffset = 0.5f;
                        zoffset = 0.5f;
                        break;
                    case 3:
                        xoffset = 0.5f;
                        yoffset = 0.5f;
                        zoffset = 0.1f;
                        break;
                    case 4:
                        xoffset = 0.5f;
                        yoffset = 0.5f;
                        zoffset = 0.9f;
                        break;
                    case 5:
                        xoffset = 0.5f;
                        yoffset = 0.1f;
                        zoffset = 0.5f;
                        break;
                }
                GL11.glVertex3f(xoffset, yoffset, zoffset);
            } else {
                GL11.glVertex3f(0.5f, 0.5f, 0.5f);
            }
        } else{
            if(secondblocksided) {
                float xoffset = 0;
                float yoffset = 0;
                float zoffset = 0;
                switch (Minecraft.getMinecraft(this).theWorld.getBlockMetadata((int) x2, (int) y2, (int) z2)) {
                    case 0:
                        xoffset = 0.5f;
                        yoffset = 0.9f;
                        zoffset = 0.5f;
                        break;
                    case 1:
                        xoffset = 0.1f;
                        yoffset = 0.5f;
                        zoffset = 0.5f;
                        break;
                    case 2:
                        xoffset = 0.9f;
                        yoffset = 0.5f;
                        zoffset = 0.5f;
                        break;
                    case 3:
                        xoffset = 0.5f;
                        yoffset = 0.5f;
                        zoffset = 0.1f;
                        break;
                    case 4:
                        xoffset = 0.5f;
                        yoffset = 0.5f;
                        zoffset = 0.9f;
                        break;
                    case 5:
                        xoffset = 0.5f;
                        yoffset = 0.1f;
                        zoffset = 0.5f;
                        break;
                }
                GL11.glVertex3d(-(x1 - x2) + xoffset,-(y1 - y2) + yoffset, -(z1 - z2) + zoffset);
            } else {
                GL11.glVertex3d(-(x1 - x2) + 0.5f, -(y1 - y2) + 0.5f, -(z1 - z2) + 0.5f);
            }
        }
        //Middle
        for(int i = xadd.size() - 1; i >= 0 ; i--){
            float xoffset = 0;
            float yoffset = 0;
            float zoffset = 0;
            switch(sideadd.get(i)){
                case 0:
                    xoffset = 0.5f;
                    yoffset = -0.1f;
                    zoffset = 0.5f;
                    break;
                case 1:
                    xoffset = 0.5f;
                    yoffset = 1.1f;
                    zoffset = 0.5f;
                    break;
                case 2:
                    xoffset = 0.5f;
                    yoffset = 0.5f;
                    zoffset = -0.1f;
                    break;
                case 3:
                    xoffset = 0.5f;
                    yoffset = 0.5f;
                    zoffset = 1.1f;
                    break;
                case 4:
                    xoffset = -0.1f;
                    yoffset = 0.5f;
                    zoffset = 0.5f;
                    break;
                case 5:
                    xoffset = 1.1f;
                    yoffset = 0.5f;
                    zoffset = 0.5f;
                    break;
            }
            GL11.glVertex3d(-(x1 - xadd.get(i)) + xoffset, -(y1 - yadd.get(i)) + yoffset, -(z1 - zadd.get(i)) + zoffset);
        }
        //Second
        if(backwired) {
            if(firstblocksided){
                float xoffset = 0;
                float yoffset = 0;
                float zoffset = 0;
                switch(Minecraft.getMinecraft(this).theWorld.getBlockMetadata(x1, y1, z1)){
                    case 0:
                        xoffset = 0.5f;
                        yoffset = 0.9f;
                        zoffset = 0.5f;
                        break;
                    case 1:
                        xoffset = 0.1f;
                        yoffset = 0.5f;
                        zoffset = 0.5f;
                        break;
                    case 2:
                        xoffset = 0.9f;
                        yoffset = 0.5f;
                        zoffset = 0.5f;
                        break;
                    case 3:
                        xoffset = 0.5f;
                        yoffset = 0.5f;
                        zoffset = 0.1f;
                        break;
                    case 4:
                        xoffset = 0.5f;
                        yoffset = 0.5f;
                        zoffset = 0.9f;
                        break;
                    case 5:
                        xoffset = 0.5f;
                        yoffset = 0.1f;
                        zoffset = 0.5f;
                        break;
                }
                GL11.glVertex3f(xoffset, yoffset, zoffset);
            } else {
                GL11.glVertex3f(0.5f, 0.5f, 0.5f);
            }
        } else{
            if(secondblocksided) {
                float xoffset = 0;
                float yoffset = 0;
                float zoffset = 0;
                switch (Minecraft.getMinecraft(this).theWorld.getBlockMetadata((int) x2, (int) y2, (int) z2)) {
                    case 0:
                        xoffset = 0.5f;
                        yoffset = 0.9f;
                        zoffset = 0.5f;
                        break;
                    case 1:
                        xoffset = 0.1f;
                        yoffset = 0.5f;
                        zoffset = 0.5f;
                        break;
                    case 2:
                        xoffset = 0.9f;
                        yoffset = 0.5f;
                        zoffset = 0.5f;
                        break;
                    case 3:
                        xoffset = 0.5f;
                        yoffset = 0.5f;
                        zoffset = 0.1f;
                        break;
                    case 4:
                        xoffset = 0.5f;
                        yoffset = 0.5f;
                        zoffset = 0.9f;
                        break;
                    case 5:
                        xoffset = 0.5f;
                        yoffset = 0.1f;
                        zoffset = 0.5f;
                        break;
                }
                GL11.glVertex3d(-(x1 - x2) + xoffset, -(y1 - y2) + yoffset, -(z1 - z2) + zoffset);
            } else {
                GL11.glVertex3d(-(x1 - x2) + 0.5f, -(y1 - y2) + 0.5f, -(z1 - z2) + 0.5f);
            }
        }
        GL11.glEnd();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDepthMask(true);
        GL11.glPopMatrix();
    }
}
