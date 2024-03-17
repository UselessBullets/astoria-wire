package net.brokenmoon.afloydwiremod.mixin;

import net.minecraft.core.net.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = Packet.class, remap = false)
public interface AccessorPacket {
    @Invoker("addIdClassMapping")
    static void callAddIdClassMapping(int i, boolean clientPacket, boolean serverPacket, Class class1) {
        throw new AssertionError();
    }
}
