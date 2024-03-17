package net.brokenmoon.afloydwiremod.mixin;

import net.minecraft.client.render.RenderBlocks;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.TextureFX;
import net.minecraft.core.Global;
import net.minecraft.core.block.Block;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = RenderBlocks.class, remap = false)
public class MixinBlockRenderer {
    @Shadow
    private int overrideBlockTexture;
    @Shadow
    private WorldSource blockAccess;

    @Shadow
    public boolean renderStandardBlock(Block block, int i, int j, int k) { return false; }
    @Shadow
	World world;
    @Inject(method = "renderBlockByRenderType(Lnet/minecraft/core/block/Block;IIII)Z", at = @At("HEAD"), cancellable = true)
    public void injectMethod(Block block, int renderType, int x, int y, int z, CallbackInfoReturnable<Boolean> cir){
        if(renderType == 28){
            cir.setReturnValue(this.renderSidedBlock(block, x, y, z));
        }
    }

    public boolean renderSidedBlock(Block block, int i, int j, int k) {
        Tessellator tessellator = Tessellator.instance;
        GL11.glDisable(GL11.GL_BLEND);
        int l = block.getBlockTextureFromSideAndMetadata(Side.BOTTOM, 0);
        if (this.overrideBlockTexture >= 0) {
            l = this.overrideBlockTexture;
        }
        float f = block.getBlockBrightness(this.blockAccess, i, j, k);
        tessellator.setColorOpaque_F(f, f, f);
        int i1 = l % Global.TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthTerrain;
        int j1 = l / Global.TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthTerrain;
        double d = (float)i1 / (float)(TextureFX.tileWidthTerrain * Global.TEXTURE_ATLAS_WIDTH_TILES);
        double d1 = ((float)i1 + ((float)TextureFX.tileWidthTerrain - 0.01f)) / (float)(TextureFX.tileWidthTerrain * Global.TEXTURE_ATLAS_WIDTH_TILES);
        double d2 = (float)j1 / (float)(TextureFX.tileWidthTerrain * Global.TEXTURE_ATLAS_WIDTH_TILES);
        double d3 = ((float)j1 + ((float)TextureFX.tileWidthTerrain - 0.01f)) / (float)(TextureFX.tileWidthTerrain * Global.TEXTURE_ATLAS_WIDTH_TILES);
        int k1 = this.blockAccess.getBlockMetadata(i, j, k);
        float f1 = 0.0f;
        float f2 = 0.15f;
        //Top
        if (k1 == 5) {
            tessellator.addVertexWithUV((float)i + 0, (float)(j + 0 +f2 ) + f1, (float)(k + 1) + f1, d, d2);
            tessellator.addVertexWithUV((float)i + 1, (float)(j + 0 + f2) - f1, (float)(k + 1) + f1, d, d3);
            tessellator.addVertexWithUV((float)i + 1, (float)(j + 0 + f2) - f1, (float)(k + 0) - f1, d1, d3);
            tessellator.addVertexWithUV((float)i + 0, (float)(j + 0 + f2) + f1, (float)(k + 0) - f1, d1, d2);
        }
        //Bottom
        if (k1 == 0) {
            tessellator.addVertexWithUV((float)i + 1, (float)(j + 1 - f2) + 0, (float)(k + 1) + f1, d, d3);
            tessellator.addVertexWithUV((float)i + 0, (float)(j + 1 - f2) + 0, (float)(k + 1) + f1, d, d2);
            tessellator.addVertexWithUV((float)i + 0, (float)(j + 1 - f2) + 0, (float)(k + 0) - f1, d1, d2);
            tessellator.addVertexWithUV((float)i + 1, (float)(j + 1 - f2) + 0, (float)(k + 0) - f1, d1, d3);
        }
        if (k1 == 1) {
            tessellator.addVertexWithUV((float)i + f2, (float)(j + 1) + f1, (float)(k + 1) + f1, d, d2);
            tessellator.addVertexWithUV((float)i + f2, (float)(j + 0) - f1, (float)(k + 1) + f1, d, d3);
            tessellator.addVertexWithUV((float)i + f2, (float)(j + 0) - f1, (float)(k + 0) - f1, d1, d3);
            tessellator.addVertexWithUV((float)i + f2, (float)(j + 1) + f1, (float)(k + 0) - f1, d1, d2);
        }
        if (k1 == 4) {
            tessellator.addVertexWithUV((float)(i + 1) + f1, (float)(j + 1) + f1, (float)(k + 1) - f2, d, d2);
            tessellator.addVertexWithUV((float)(i + 1) + f1, (float)(j + 0) - f1, (float)(k + 1) - f2, d, d3);
            tessellator.addVertexWithUV((float)(i + 0) - f1, (float)(j + 0) - f1, (float)(k + 1) - f2, d1, d3);
            tessellator.addVertexWithUV((float)(i + 0) - f1, (float)(j + 1) + f1, (float)(k + 1) - f2, d1, d2);
        }
        if (k1 == 3) {
            tessellator.addVertexWithUV((float)(i + 1) + f1, (float)(j + 0) - f1, (float)k + f2, d1, d3);
            tessellator.addVertexWithUV((float)(i + 1) + f1, (float)(j + 1) + f1, (float)k + f2, d1, d2);
            tessellator.addVertexWithUV((float)(i + 0) - f1, (float)(j + 1) + f1, (float)k + f2, d, d2);
            tessellator.addVertexWithUV((float)(i + 0) - f1, (float)(j + 0) - f1, (float)k + f2, d, d3);
        }
        if (k1 == 2) {
            tessellator.addVertexWithUV((float)(i + 1) - f2, (float)(j + 0) - f1, (float)(k + 1) + f1, d1, d3);
            tessellator.addVertexWithUV((float)(i + 1) - f2, (float)(j + 1) + f1, (float)(k + 1) + f1, d1, d2);
            tessellator.addVertexWithUV((float)(i + 1) - f2, (float)(j + 1) + f1, (float)(k + 0) - f1, d, d2);
            tessellator.addVertexWithUV((float)(i + 1) - f2, (float)(j + 0) - f1, (float)(k + 0) - f1, d, d3);
        }
        return true;
    }
}
