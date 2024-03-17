package net.brokenmoon.afloydwiremod;

import net.brokenmoon.afloydwiremod.api.AbstractWireTileEntity;
import net.brokenmoon.afloydwiremod.item.ToolProgrammer;
import net.brokenmoon.afloydwiremod.item.ToolWiring;
import net.brokenmoon.afloydwiremod.mixin.AccessorPacket;
import net.brokenmoon.afloydwiremod.packet.WiremodPacketSyncIO;
import net.brokenmoon.afloydwiremod.packet.WiremodProgrammerGuiPacket;
import net.brokenmoon.afloydwiremod.packet.WiremodProgrammerPacket;
import net.brokenmoon.afloydwiremod.packet.WiremodSettingsPacket;
import net.brokenmoon.afloydwiremod.packet.WiremodWireGuiPacket;
import net.brokenmoon.afloydwiremod.packet.WiremodWiringGuiPacket;
import net.brokenmoon.afloydwiremod.packet.WiremodWiringPacket;
import net.brokenmoon.afloydwiremod.ter.TERDisplay;
import net.brokenmoon.afloydwiremod.ter.TERWire;
import net.brokenmoon.afloydwiremod.tile.ChipTile;
import net.brokenmoon.afloydwiremod.tile.DisplayTile;
import net.brokenmoon.afloydwiremod.tile.RedstoneLinkTile;
import net.brokenmoon.afloydwiremod.tileentity.ChipTileEntity;
import net.brokenmoon.afloydwiremod.tileentity.DisplayTileEntity;
import net.brokenmoon.afloydwiremod.tileentity.RedstoneLinkTileEntity;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.render.block.model.BlockModelRenderBlocks;
import net.minecraft.client.sound.block.BlockSounds;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.BlockBuilder;
import turniplabs.halplibe.helper.EntityHelper;
import turniplabs.halplibe.helper.ItemHelper;
import turniplabs.halplibe.helper.NetworkHelper;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.util.RecipeEntrypoint;


public class WireMod implements ModInitializer, RecipeEntrypoint {

    public static final String MOD_ID = "afloydwiremod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final WiremodConfig config = new WiremodConfig();

    //Tile
    public static Block ChipTile = new BlockBuilder(MOD_ID)
		.setHardness(1.5f)
		.setResistance(6f)
		.setTextures("chip.png")
		.setBlockSound(BlockSounds.METAL)
		.setBlockModel(new BlockModelRenderBlocks(28))
		.build(new ChipTile("chipTile", config.chipTileID, Material.metal));
    public static Block LinkTileInactive = new BlockBuilder(MOD_ID)
		.setHardness(1.5f)
		.setResistance(6f)
		.setTextures("linkOff.png")
		.setBlockSound(BlockSounds.STONE)
		.build(new RedstoneLinkTile("linkTile", config.linkTileInactiveID, Material.metal, false));
    public static Block LinkTileActive = new BlockBuilder(MOD_ID)
		.setHardness(1.5f)
		.setResistance(6f)
		.setTextures("display.png")
		.setBlockSound(BlockSounds.STONE)
		.addTags(BlockTags.NOT_IN_CREATIVE_MENU)
		.build(new RedstoneLinkTile("linkTile", config.linkTileActiveID, Material.stone, true));
    public static Block ScreenTile = new BlockBuilder(MOD_ID)
		.setHardness(1.5f)
		.setResistance(6f)
		.setTextures("display.png")
		.setBlockSound(BlockSounds.GLASS)
		.setBlockModel(new BlockModelRenderBlocks(28))
		.build(new DisplayTile("displayTile", config.displayTileID, Material.glass));
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
		NetworkHelper.register(WiremodProgrammerGuiPacket.class, true, true);
		NetworkHelper.register(WiremodProgrammerPacket.class, true, true);
		NetworkHelper.register(WiremodWiringGuiPacket.class, true, true);
		NetworkHelper.register(WiremodPacketSyncIO.class, true, true);
		NetworkHelper.register(WiremodWiringPacket.class, true, true);
		NetworkHelper.register(WiremodWireGuiPacket.class, true, true);
		NetworkHelper.register(WiremodSettingsPacket.class, true, true);

        EntityHelper.createTileEntity(ChipTileEntity.class, "Chip");
        EntityHelper.createTileEntity(RedstoneLinkTileEntity.class, "Redstone Link");
        EntityHelper.createSpecialTileEntity(AbstractWireTileEntity.class, new TERWire(), "Wire");
        EntityHelper.createSpecialTileEntity(DisplayTileEntity.class, new TERDisplay(), "Display");
    }

	@Override
	public void onRecipesReady() {
		//Recipes
		//Silica
		RecipeBuilder.Shapeless(MOD_ID)
			.addInput(new ItemStack(Item.dustRedstone, 1))
			.addInput(new ItemStack(Item.dustRedstone, 1))
			.addInput(new ItemStack(Block.sand, 1))
			.create("red_silica", new ItemStack(RedSilica, 8));
		//Dies
		RecipeBuilder.Furnace(MOD_ID)
			.setInput(new ItemStack(ChipDie))
			.create("chip_die", new ItemStack(RedSilica));
		RecipeBuilder.BlastFurnace(MOD_ID)
			.setInput(new ItemStack(BrokenDie))
			.create("chip_die", new ItemStack(RedSilica));
		//Tools
		RecipeBuilder.Shaped(MOD_ID)
			.setShape(
				"ABC",
				"#D#",
				"#D#")
			.addInput('A', Item.oreRawIron) // used to be nugget
			.addInput('B', Item.ingotIron)
			.addInput('C', RedSilica)
			.addInput('D', Item.stick)
			.create("tool_wiring", ToolWiring.getDefaultStack());
		RecipeBuilder.Shaped(MOD_ID)
			.setShape(
				"ABC",
				"#D#",
				"#D#")
			.addInput('A', Item.oreRawIron) // used to be nugget
			.addInput('B', Item.ingotIron)
			.addInput('C', ChipDie)
			.addInput('D', Item.stick)
			.create("tool_wiring", ToolProgrammer.getDefaultStack());
		//Blocks
		RecipeBuilder.Shaped(MOD_ID)
			.setShape(
				"#A#",
				"ABA",
				"#A#")
			.addInput('A', Item.oreRawIron) // used to be nugget
			.addInput('B', ChipDie)
			.create("chip_tile", ChipTile.getDefaultStack());
		RecipeBuilder.Shaped(MOD_ID)
			.setShape(
				"#A#",
				"BCB",
				"#B#")
			.addInput('A', Block.glass)
			.addInput('B', Item.oreRawIron) // used to be nugget
			.addInput('C', ChipDie)
			.create("screen_tile", ScreenTile.getDefaultStack());
		RecipeBuilder.Shaped(MOD_ID)
			.setShape(
				"#A#",
				"BCB",
				"#B#")
			.addInput('A', Block.blockRedstone)
			.addInput('B', Item.oreRawIron) // used to be nugget
			.addInput('C', ChipDie)
			.create("link_tile", LinkTileInactive.getDefaultStack());
	}
}
