package net.brokenmoon.afloydwiremod;

import net.brokenmoon.afloydwiremod.api.AbstractWireTileEntity;
import net.brokenmoon.afloydwiremod.item.ToolWiring;
import net.brokenmoon.afloydwiremod.mixin.AccessorPacket;
import net.brokenmoon.afloydwiremod.packet.*;
import net.brokenmoon.afloydwiremod.ter.TERDisplay;
import net.brokenmoon.afloydwiremod.ter.TERWire;
import net.brokenmoon.afloydwiremod.tile.ChipTile;
import net.brokenmoon.afloydwiremod.item.ToolProgrammer;
import net.brokenmoon.afloydwiremod.tile.DisplayTile;
import net.brokenmoon.afloydwiremod.tile.RedstoneLinkTile;
import net.brokenmoon.afloydwiremod.tileentity.*;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.sound.block.BlockSounds;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.BlockHelper;
import turniplabs.halplibe.helper.EntityHelper;
import turniplabs.halplibe.helper.ItemHelper;
import turniplabs.halplibe.helper.RecipeHelper;


public class WireMod implements ModInitializer {

    public static final String MOD_ID = "afloydwiremod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final WiremodConfig config = new WiremodConfig();

    public WireMod(){
        AccessorPacket.callAddIdClassMapping(config.programmerGuiPacket, true, true, WiremodProgrammerGuiPacket.class);
        AccessorPacket.callAddIdClassMapping(config.programmingPacket, true, true, WiremodProgrammerPacket.class);
        AccessorPacket.callAddIdClassMapping(config.wiringGuiPacket, true, true, WiremodWiringGuiPacket.class);
        AccessorPacket.callAddIdClassMapping(config.syncPacket, true, true, WiremodPacketSyncIO.class);
        AccessorPacket.callAddIdClassMapping(config.wiringPacket, true, true, WiremodWiringPacket.class);
        AccessorPacket.callAddIdClassMapping(config.wiringSettingsPacket, true, true, WiremodWireGuiPacket.class);
        AccessorPacket.callAddIdClassMapping(config.chipSettingsPacket, true, true, WiremodSettingsPacket.class);
    }

    //Tile
    public static Block ChipTile = BlockHelper.createBlock(MOD_ID, new ChipTile(config.chipTileID, Material.metal), "chipTile", "chip.png", BlockSounds.METAL,1.5f, 6f, 0 );
    public static Block LinkTileInactive = BlockHelper.createBlock(MOD_ID, new RedstoneLinkTile(config.linkTileInactiveID, Material.metal, false), "linkTile", "linkOff.png", BlockSounds.STONE, 1.5f, 6f, 0);
    public static Block LinkTileActive = BlockHelper.createBlock(MOD_ID, new RedstoneLinkTile(config.linkTileActiveID, Material.metal, true), "linkTile", "linkOn.png", BlockSounds.STONE, 1.5f, 6f, 0);
    public static Block ScreenTile = BlockHelper.createBlock(MOD_ID, new DisplayTile(config.displayTileID, Material.glass), "displayTile", "display.png", BlockSounds.GLASS, 1.5f, 6f, 0);
    //Item
    public static Item ToolProgrammer = ItemHelper.createItem(MOD_ID, new ToolProgrammer(config.programmerItemID), "toolProgrammer", "progtool.png");
    public static ToolWiring ToolWiringClass = new ToolWiring(config.wiringItemID);
    public static Item ToolWiring = ItemHelper.createItem(MOD_ID, ToolWiringClass, "toolWiring", "wiretool.png");
    public static Item RedSilica = ItemHelper.createItem(MOD_ID, new Item(config.redsilicaID), "redSilica", "redsilica.png");
    public static Item ChipDie = ItemHelper.createItem(MOD_ID, new Item(config.dieID), "chipDie", "circuitdie.png");
    public static Item BrokenDie = ItemHelper.createItem(MOD_ID, new Item(config.dieOvercookedID), "chipDieBroken", "circuitdead.png");

    @Override
    public void onInitialize() {
        LOGGER.info("WireMod initialized.");
        LinkTileActive.notInCreativeMenu = true;
        EntityHelper.createTileEntity(ChipTileEntity.class, "Chip");
        EntityHelper.createTileEntity(RedstoneLinkTileEntity.class, "Redstone Link");
        EntityHelper.createSpecialTileEntity(AbstractWireTileEntity.class, new TERWire(), "Wire");
        EntityHelper.createSpecialTileEntity(DisplayTileEntity.class, new TERDisplay(), "Display");

        //Recipes
        //Silica
        RecipeHelper.Crafting.createShapelessRecipe(RedSilica, 8, new Object[]{new ItemStack(Item.dustRedstone, 1), new ItemStack(Item.dustRedstone, 1), new ItemStack(Item.dustRedstone, 1), new ItemStack(Block.sand, 1)});
        //Dies
        RecipeHelper.Smelting.createRecipe(ChipDie, RedSilica);
        RecipeHelper.Blasting.createRecipe(BrokenDie, RedSilica);
        //Tools
        RecipeHelper.Crafting.createRecipe(ToolWiring, 1, new Object[]{"ABC", "#D#", "#D#", 'A', Item.nuggetIron, 'B', Item.ingotIron, 'C', RedSilica, 'D', Item.stick});
        RecipeHelper.Crafting.createRecipe(ToolProgrammer, 1, new Object[]{"ABC", "#D#", "#D#", 'A', Item.nuggetIron, 'B', Item.ingotIron, 'C', ChipDie, 'D', Item.stick});
        //Blocks
        RecipeHelper.Crafting.createRecipe(ChipTile, 1, new Object[]{"#A#", "ABA", "#A#", 'A', Item.nuggetIron, 'B', ChipDie});
        RecipeHelper.Crafting.createRecipe(ScreenTile, 1, new Object[]{"#A#", "BCB", "#B#", 'A', Block.glass, 'B', Item.nuggetIron, 'C', ChipDie});
        RecipeHelper.Crafting.createRecipe(LinkTileInactive, 1, new Object[]{"#A#", "BCB", "#B#", 'A', Block.blockRedstone, 'B', Item.nuggetIron, 'C', ChipDie});
    }
}
