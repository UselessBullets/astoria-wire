package net.brokenmoon.afloydwiremod.tile;

import net.brokenmoon.afloydwiremod.api.AbstractWireTileSided;
import net.brokenmoon.afloydwiremod.tileentity.DisplayTileEntity;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;

public class DisplayTile extends AbstractWireTileSided {
    public DisplayTile(String key, int i, Material material) {
        super(key, i, material);
    }

    @Override
    protected TileEntity getNewBlockEntity() {
        return new DisplayTileEntity();
    }
}
