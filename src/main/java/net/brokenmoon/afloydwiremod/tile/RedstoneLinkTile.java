package net.brokenmoon.afloydwiremod.tile;

import net.brokenmoon.afloydwiremod.WireMod;
import net.brokenmoon.afloydwiremod.api.AbstractWireTile;
import net.brokenmoon.afloydwiremod.tileentity.RedstoneLinkTileEntity;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import net.minecraft.src.*;

import java.util.Random;

public class RedstoneLinkTile extends AbstractWireTile {

    boolean isActive;

    public RedstoneLinkTile(int i, Material material, boolean isActive) {
        super(i, material);
        this.isActive = isActive;
        this.setTickOnLoad(true);
    }

    public static void updateLinkBlockState(boolean flag, World world, int x, int y, int z) {
        if (flag) {
            world.setBlockAndMetadataWithNotify(x, y, z, WireMod.LinkTileActive.id, 1);
        }
        if (!flag) {
            world.setBlockAndMetadataWithNotify(x, y, z, WireMod.LinkTileInactive.id, 1);
        }
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int tickRate() {
        return 2;
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        if (world.getBlockMetadata(x, y, z) == 0) {
            world.setBlockTileEntity(x, y, z, new RedstoneLinkTileEntity());
        }
        world.notifyBlocksOfNeighborChange(x, y - 1, z, this.id);
        world.notifyBlocksOfNeighborChange(x, y + 1, z, this.id);
        world.notifyBlocksOfNeighborChange(x - 1, y, z, this.id);
        world.notifyBlocksOfNeighborChange(x + 1, y, z, this.id);
        world.notifyBlocksOfNeighborChange(x, y, z - 1, this.id);
        world.notifyBlocksOfNeighborChange(x, y, z + 1, this.id);
    }

    @Override
    public void onBlockRemoval(World world, int x, int y, int z) {
        if (world.getBlockTileEntity(x, y, z) != null && !((RedstoneLinkTileEntity) world.getBlockTileEntity(x, y, z)).shouldnotremove)
            super.onBlockRemoval(world, x, y, z);
        world.notifyBlocksOfNeighborChange(x, y - 1, z, this.id);
        world.notifyBlocksOfNeighborChange(x, y + 1, z, this.id);
        world.notifyBlocksOfNeighborChange(x - 1, y, z, this.id);
        world.notifyBlocksOfNeighborChange(x + 1, y, z, this.id);
        world.notifyBlocksOfNeighborChange(x, y, z - 1, this.id);
        world.notifyBlocksOfNeighborChange(x, y, z + 1, this.id);
    }

    @Override
    public void updateTick(World world, int i, int j, int k, Random random) {
        RedstoneLinkTileEntity link = (RedstoneLinkTileEntity) world.getBlockTileEntity(i, j, k);
        if (world.isBlockGettingPowered(i, j, k) || world.isBlockIndirectlyGettingPowered(i, j, k)) {
            link.outputs[0].floatvalue = 1.0f;
            link.updateIO();
        } else {
            link.outputs[0].floatvalue = 0.0f;
            link.updateIO();
        }
    }

    @Override
    public boolean canProvidePower() {
        return true;
    }

    @Override
    public boolean isPoweringTo(WorldSource iblockaccess, int i, int j, int k, int l) {
        return this.isActive;
    }

    @Override
    public boolean isIndirectlyPoweringTo(World world, int i, int j, int k, int l) {
        return this.isPoweringTo(world, i, j, k, l);
    }

    @Override
    protected TileEntity getBlockEntity() {
        return new RedstoneLinkTileEntity();
    }

    @Override
    public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
        world.scheduleBlockUpdate(i, j, k, this.id, this.tickRate());
    }

    @Override
    public int idDropped(int i, Random random) {
        return WireMod.LinkTileInactive.id;
    }

    @Override
    protected int damageDropped(int i) {
        return i;
    }
}
