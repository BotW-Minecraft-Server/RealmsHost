package link.botwmcs.samchai.realmshost.network;

import link.botwmcs.samchai.realmshost.capability.town.Town;
import link.botwmcs.samchai.realmshost.network.c2s.ChooseJobC2SPacket;
import link.botwmcs.samchai.realmshost.network.c2s.ChooseTownC2SPacket;
import link.botwmcs.samchai.realmshost.network.c2s.RespawnPlayerOnPlaceC2SPacket;
import link.botwmcs.samchai.realmshost.util.CapabilitiesHandler;
import link.botwmcs.samchai.realmshost.util.PlayerUtilities;
import link.botwmcs.samchai.realmshost.util.ServerUtilities;
import link.botwmcs.samchai.realmshost.util.TownHandler;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;
import java.util.Map;

public class C2SHandler {
    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(ChooseJobC2SPacket.TYPE, C2SHandler::onChooseJob);
        ServerPlayNetworking.registerGlobalReceiver(ChooseTownC2SPacket.TYPE, C2SHandler::onChooseTown);
        ServerPlayNetworking.registerGlobalReceiver(RespawnPlayerOnPlaceC2SPacket.TYPE, C2SHandler::raisePlayerRespawnPlace);
    }

    private static void onChooseJob(ChooseJobC2SPacket packet, ServerPlayer serverPlayer, PacketSender sender) {
        serverPlayer.sendSystemMessage(Component.nullToEmpty("You chose " + packet.jobName() + "!"));
        if (PlayerUtilities.isPlayerFirstJoinServer(serverPlayer)) {
            PlayerUtilities.openTownChooseScreen(serverPlayer, true);
        }

        CapabilitiesHandler.setPlayerJob(serverPlayer, packet.jobName());
    }
    private static void onChooseTown(ChooseTownC2SPacket packet, ServerPlayer serverPlayer, PacketSender sender) {
        serverPlayer.sendSystemMessage(Component.nullToEmpty("You chose " + packet.townName() + "!"));
        CapabilitiesHandler.setPlayerTown(serverPlayer, packet.townName());
        Map<String, Town> towns = TownHandler.getTowns(serverPlayer.serverLevel());
        Town town = towns.get(packet.townName());
        TownHandler.addTownResidents(town, serverPlayer);
    }
    private static void raisePlayerRespawnPlace(RespawnPlayerOnPlaceC2SPacket packet, ServerPlayer serverPlayer, PacketSender sender) {
        ServerUtilities.respawnPlayer(serverPlayer.getServer().getLevel(packet.level()), serverPlayer, packet.pos());
    }
}
