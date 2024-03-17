package net.brokenmoon.afloydwiremod.api;

import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;

public abstract class AbstractWireTileSided extends AbstractWireTile{
    public AbstractWireTileSided(String key, int i, Material material) {
        super(key, i, material);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    @Override
    public void onBlockPlaced(World world, int x, int y, int z, Side side, EntityLiving player, double sideHeight) {
        int l = side.getId();
        int i1 = 5;
        if (l == 1 && this.canPlaceOnTop(world, x, y - 1, z)) {
            i1 = 5;
        } else if (l == 2 && world.isBlockNormalCube(x, y, z + 1)) {
            i1 = 4;
        } else if (l == 3 && world.isBlockNormalCube(x, y, z - 1)) {
            i1 = 3;
        } else if (l == 4 && world.isBlockNormalCube(x + 1, y, z)) {
            i1 = 2;
        } else if (l == 5 && world.isBlockNormalCube(x - 1, y, z)) {
            i1 = 1;
        } else if (l == 0 && world.isBlockNormalCube(x, y + 1, z)) {
            i1 = 0;
        }
        world.setBlockMetadataWithNotify(x, y, z, i1);
    }

    @Override
    public void onBlockAdded(World world, int i, int j, int k) {
        super.onBlockAdded(world, i, j, k);
        if (world.isBlockNormalCube(i - 1, j, k)) {
            world.setBlockMetadataWithNotify(i, j, k, 1);
        } else if (world.isBlockNormalCube(i + 1, j, k)) {
            world.setBlockMetadataWithNotify(i, j, k, 2);
        } else if (world.isBlockNormalCube(i, j, k - 1)) {
            world.setBlockMetadataWithNotify(i, j, k, 3);
        } else if (world.isBlockNormalCube(i, j, k + 1)) {
            world.setBlockMetadataWithNotify(i, j, k, 4);
        } else if (world.isBlockNormalCube(i, j + 1, k)) {
            world.setBlockMetadataWithNotify(i, j, k, 0);
        } else if (this.canPlaceOnTop(world, i, j - 1, k)) {
            world.setBlockMetadataWithNotify(i, j, k, 5);
        }
        this.dropTileIfCantStay(world, i, j, k);
    }

    private boolean canPlaceOnTop(World world, int i, int j, int k) {
        return world.isBlockNormalCube(i, j, k);
    }

    @Override
    public boolean canPlaceBlockAt(World world, int i, int j, int k) {
        if (world.isBlockNormalCube(i - 1, j, k)) {
            return true;
        }
        if (world.isBlockNormalCube(i + 1, j, k)) {
            return true;
        }
        if (world.isBlockNormalCube(i, j, k - 1)) {
            return true;
        }
        if (world.isBlockNormalCube(i, j, k + 1)) {
            return true;
        }
        if (world.isBlockNormalCube(i, j + 1, k)) {
            return true;
        }
        return world.canPlaceOnSurfaceOfBlock(i, j - 1, k);
    }

    @Override
    public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
        if (this.dropTileIfCantStay(world, i, j, k)) {
            int i1 = world.getBlockMetadata(i, j, k);
            boolean flag = true;
            switch(i1){
                case 0:
                    if(world.isBlockNormalCube(i, j + 1, k))
                        flag = false;
                    break;
                case 1:
                    if(world.isBlockNormalCube(i - 1, j, k))
                        flag = false;
                    break;
                case 2:
                    if(world.isBlockNormalCube(i + 1, j, k))
                        flag = false;
                    break;
                case 3:
                    if(world.isBlockNormalCube(i, j, k - 1))
                        flag = false;
                    break;
                case 4:
                    if(world.isBlockNormalCube(i, j, k + 1))
                        flag = false;
                    break;
                case 5:
                    if(this.canPlaceOnTop(world, i, j - 1, k))
                        flag = false;
                    break;
            }
            if (flag) {
				this.dropBlockWithCause(world, EnumDropCause.WORLD, i, j, k, world.getBlockMetadata(i, j, k), world.getBlockTileEntity(i, j, k));
                world.setBlockWithNotify(i, j, k, 0);
            }
        }
    }

    private boolean dropTileIfCantStay(World world, int i, int j, int k) {
        if (!this.canPlaceBlockAt(world, i, j, k)) {
            this.dropBlockWithCause(world, EnumDropCause.WORLD, i, j, k, world.getBlockMetadata(i, j, k), world.getBlockTileEntity(i, j, k));
            world.setBlockWithNotify(i, j, k, 0);
            return false;
        }
        return true;
    }
    @Override
    public AABB getSelectedBoundingBoxFromPool(World world, int i, int j, int k) {
        this.setBlockBoundsBasedOnState(world, i, j, k);
        return super.getSelectedBoundingBoxFromPool(world, i, j, k);
    }
    @Override
    public AABB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
        this.setBlockBoundsBasedOnState(world, i, j, k);
        return super.getCollisionBoundingBoxFromPool(world, i, j, k);
    }
    @Override
    public void setBlockBoundsBasedOnState(World world, int i, int j, int k) {
        this.setBlockBoundsForBlockRender(world.getBlockMetadata(i, j, k));
    }
    @Override
    public void setBlockBoundsForItemRender() {
        float f = 0.1875f;
        this.setBlockBounds(0.0f, 0.5f - f / 2.0f, 0.0f, 1.0f, 0.5f + f / 2.0f, 1.0f);
    }

    public void setBlockBoundsForBlockRender(int i) {
        float f = 0.1875f;
        if (i == 4) {
            this.setBlockBounds(0.0f, 0.0f, 1.0f - f, 1.0f, 1.0f, 1.0f);
        }
        if (i == 3) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, f);
        }
        if (i == 2) {
            this.setBlockBounds(1.0f - f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        if (i == 1) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, f, 1.0f, 1.0f);
        }
        if (i == 0) {
            this.setBlockBounds(0.0f, 1.0f - f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        if (i == 5) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, f, 1.0f);
        }
    }

    @Override
    public boolean isSolidRender() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
}
