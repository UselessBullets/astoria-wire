package net.brokenmoon.afloydwiremod.api;

import net.brokenmoon.afloydwiremod.WireMod;
import net.brokenmoon.afloydwiremod.item.ToolWiring;
import net.brokenmoon.afloydwiremod.mixinInterfaces.IEntityPlayer;
import net.minecraft.core.block.BlockTileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;
import net.minecraft.server.entity.player.EntityPlayerMP;

public abstract class AbstractWireTile extends BlockTileEntity {
    public AbstractWireTile(int i, Material material) {
        super(i, material);
    }
    @Override
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player){
        AbstractWireTileEntity wireEntity = (AbstractWireTileEntity)world.getBlockTileEntity(x, y, z);
        if (!world.isMultiplayerAndNotHost) {
            if(player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().itemID == WireMod.ToolProgrammer.id && !wireEntity.initialized) {
                this.displayProgrammingGui(player, wireEntity);
                return true;
            } else if(player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().itemID == WireMod.ToolWiring.id && wireEntity.initialized) {
                ToolWiring tool = ((ToolWiring)player.inventory.getCurrentItem().getItem());
                this.displayWiringGui(player, wireEntity, tool, x, y, z);
                return true;
            } else if(player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().itemID == WireMod.ToolProgrammer.id && wireEntity.initialized && wireEntity.hasSettings) {
                this.displaySettingsGui(player, wireEntity);
                return true;
            }
        }
        return false;
    }
    public void displayWiringGui(EntityPlayer player, AbstractWireTileEntity chip, ToolWiring tool, int x, int y, int z) {
        if(player instanceof EntityPlayerMP) {
            //Multiplayer
            ((IEntityPlayer)player).displayGuiWiring(tool, chip, x, y, z);
            return;
        }
        //Singleplayer
        ((IEntityPlayer)player).displayGuiWiring(tool, chip, x, y, z);
    }
    public void displayProgrammingGui(EntityPlayer player, AbstractWireTileEntity chip){
        if(player instanceof EntityPlayerMP) {
            //Multiplayer
            ((IEntityPlayer)player).displayGuiProgrammer(chip);
            return;
        }
        //Singleplayer
        ((IEntityPlayer)player).displayGuiProgrammer(chip);
    }
    public void displaySettingsGui(EntityPlayer player, AbstractWireTileEntity chip){
        if(player instanceof EntityPlayerMP) {
            //Multiplayer
            ((IEntityPlayer)player).displayGuiSettings(chip);
            return;
        }
        //Singleplayer
        ((IEntityPlayer)player).displayGuiSettings(chip);
    }
    @Override
    public void onBlockRemoval(World world, int x, int y, int z) {
        AbstractWireTileEntity tile = (AbstractWireTileEntity) world.getBlockTileEntity(x, y, z);
        tile.prepForDelete();
        super.onBlockRemoval(world, x, y, z);
    }
}
