package net.brokenmoon.afloydwiremod.tile;

import net.brokenmoon.afloydwiremod.api.AbstractWireTileSided;
import net.brokenmoon.afloydwiremod.tileentity.ChipTileEntity;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;

public class ChipTile extends AbstractWireTileSided {
    public ChipTile(int i, Material material) {
        super(i, material);
    }

    @Override
    protected TileEntity getNewBlockEntity() {
        return new ChipTileEntity();
    }
}
