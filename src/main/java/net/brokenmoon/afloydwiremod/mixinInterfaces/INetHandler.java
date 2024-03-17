package net.brokenmoon.afloydwiremod.mixinInterfaces;

import net.brokenmoon.afloydwiremod.packet.*;

public interface INetHandler {
    void wiremodHandleOpenProgrammerGUI(WiremodProgrammerGuiPacket packet);

    void wiremodHandleProgramTile(WiremodProgrammerPacket wiremodProgrammerPacket);

    void wiremodHandleOpenWiringGUI(WiremodWiringGuiPacket wiremodWiringGuiPacket);

    void wiremodHandleIODisc(WiremodPacketSyncIO wiremodPacketSyncIO);

    void wiremodHandleWireChips(WiremodWiringPacket wiremodWiringPacket);

    void wiremodHandleWireToolSettingsGui(WiremodWireGuiPacket wiremodWireGuiPacket);

    void wiremodHandleSettings(WiremodSettingsPacket wiremodSettingsPacket);
}
