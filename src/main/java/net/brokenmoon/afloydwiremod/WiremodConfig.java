package net.brokenmoon.afloydwiremod;
import com.moandjiezana.toml.Toml;

import java.io.*;

import static net.brokenmoon.afloydwiremod.WireMod.LOGGER;

public class WiremodConfig {

    //Packet ID's
    public int programmerGuiPacket;
    public int programmingPacket;
    public int wiringGuiPacket;
    public int syncPacket;
    public int wiringPacket;
    public int wiringSettingsPacket;
    public int chipSettingsPacket;
    //Tiles
    public int chipTileID;
    public int linkTileInactiveID;
    public int linkTileActiveID;
    public int displayTileID;
    //Items
    public int programmerItemID;
    public int wiringItemID;
    public int redsilicaID;
    public int dieID;
    public int dieOvercookedID;
    public int scrollrate;


    public WiremodConfig(){
        Toml toml = new Toml().read(this.getConfig());
        //Packets
        programmingPacket = toml.getLong("ids.packet.programming", (long)109).intValue();
        programmerGuiPacket = toml.getLong("ids.packet.programmerGui", (long)110).intValue();
        wiringPacket = toml.getLong("ids.packet.wiring", (long)111).intValue();
        wiringGuiPacket = toml.getLong("ids.packet.wiringGui", (long)112).intValue();
        wiringSettingsPacket = toml.getLong("ids.packet.wiringSettingsGui", (long)113).intValue();
        syncPacket = toml.getLong("ids.packet.sync", (long)114).intValue();
        chipSettingsPacket = toml.getLong("ids.packet.chipSettingsGui", (long)115).intValue();
        //Tiles
        chipTileID = toml.getLong("ids.tile.chipTile", (long)905).intValue();
        linkTileInactiveID = toml.getLong("ids.tile.linkTileInactive", (long)906).intValue();
        linkTileActiveID = toml.getLong("ids.tile.linkTileActive", (long)907).intValue();
        displayTileID = toml.getLong("ids.tile.displayTile", (long)908).intValue();
        //Items
        programmerItemID = toml.getLong("ids.item.programmingTool", (long)909).intValue();
        wiringItemID = toml.getLong("ids.item.wiringTool", (long)910).intValue();
        redsilicaID = toml.getLong("ids.item.redSilica", (long)911).intValue();
        dieID = toml.getLong("ids.item.chipDie", (long)912).intValue();
        dieOvercookedID = toml.getLong("ids.item.chipDieOvercooked", (long)913).intValue();
        //Various settings
        scrollrate = toml.getLong("misc.scrollrate", (long)5).intValue();
        
    }
    public static File getConfig() {
        File config = new File("config/AWM.toml");
        if (!config.exists()) {
            LOGGER.warn("Config For AWM Not Found! Creating new config based upon default :)");
            InputStream in;
            OutputStream out;
            try {
                File configDir = new File("config");
                if (!configDir.exists())
                    configDir.mkdir();
                in = WireMod.class.getClassLoader().getResourceAsStream("assets/afloydwiremod/config.toml");
                out = new FileOutputStream(config);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
                return getConfig();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            LOGGER.info("Config for AWM loaded!");
            return config;
        }
    }
}
