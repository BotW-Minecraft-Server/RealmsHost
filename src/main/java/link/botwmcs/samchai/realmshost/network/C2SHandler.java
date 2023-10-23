package link.botwmcs.samchai.realmshost.network;

import link.botwmcs.samchai.realmshost.network.c2s.ChooseJobC2SPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class C2SHandler {
    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(ChooseJobC2SPacket.TYPE, C2SHandler::onChooseJob);
    }

    private static void onChooseJob(ChooseJobC2SPacket packet, ServerPlayer serverPlayer, PacketSender sender) {
        serverPlayer.sendSystemMessage(Component.nullToEmpty("You chose " + packet.jobName() + "!"));
    }
}
