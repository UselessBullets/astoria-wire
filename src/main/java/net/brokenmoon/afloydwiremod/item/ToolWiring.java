package net.brokenmoon.afloydwiremod.item;

import net.brokenmoon.afloydwiremod.api.AbstractWireTileEntity;
import net.brokenmoon.afloydwiremod.mixinInterfaces.IEntityPlayer;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import net.minecraft.server.entity.player.EntityPlayerMP;

import java.util.ArrayList;

public class ToolWiring extends Item {

    public int x = 0;
    public int y = 0;
    public int z = 0;
    public ArrayList<Integer> xadd = new ArrayList<Integer>();
    public ArrayList<Integer> yadd = new ArrayList<Integer>();
    public ArrayList<Integer> zadd = new ArrayList<Integer>();
    public ArrayList<Integer> sideadd = new ArrayList<Integer>();
    public String type = "unpaired";
    public int slot;

    public float red = 1;
    public float green = 0;
    public float blue = 0;
    public float alpha = 1;
    public float width = 0.5f;

    public ToolWiring(int i) {
        super(i);
        this.maxStackSize = 1;
    }

    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, double heightPlaced) {
        if(entityplayer.isSneaking()){
            if (!world.isClientSide) {
                this.displayWireSettings(entityplayer);
                return true;
            }
        } else {
            if(!(world.getBlockTileEntity(i, j, k) instanceof AbstractWireTileEntity))
            xadd.add(i);
            yadd.add(j);
            zadd.add(k);
            sideadd.add(l);
            return false;
        }
        return false;
    }

    private void displayWireSettings(EntityPlayer player) {
        if(player instanceof EntityPlayerMP) {
            //Multiplayer
            ((IEntityPlayer)player).displayGuiWireSettings();
            return;
        }
        //Singleplayer
        ((IEntityPlayer)player).displayGuiWireSettings();
        }

        @Override
        public boolean isFull3D() {
            return true;
        }
    }
