package link.botwmcs.samchai.realmshost.util;

import link.botwmcs.samchai.realmshost.RealmsHost;
import link.botwmcs.samchai.realmshost.capability.Account;
import link.botwmcs.samchai.realmshost.capability.AccountHandler;
import link.botwmcs.samchai.realmshost.capability.DeathCounter;
import link.botwmcs.samchai.realmshost.capability.PlayerInfo;
import link.botwmcs.samchai.realmshost.network.s2c.OpenChooseJobScreenS2CPacket;
import link.botwmcs.samchai.realmshost.network.s2c.OpenChooseTownScreenS2CPacket;
import link.botwmcs.samchai.realmshost.network.s2c.OpenPlayerInfoScreenS2CPacket;
import link.botwmcs.samchai.realmshost.network.s2c.SendSystemToastS2CPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

import java.util.List;

public class PlayerUtilities {
    public static boolean isPlayerFirstJoinServer(Player player) {
        return AccountHandler.ACCOUNT_COMPONENT_KEY.get(player).isPlayerFirstJoinServer();
    }

    public static void openJobChooseScreen(ServerPlayer player, boolean showBackground) {
        ServerPlayNetworking.send(player, new OpenChooseJobScreenS2CPacket(showBackground));
    }

    public static void openTownChooseScreen(ServerPlayer player, boolean showBackground) {
        ServerPlayNetworking.send(player, new OpenChooseTownScreenS2CPacket(TownHandler.getTownList(player.level()), showBackground));
    }

    public static void openPlayerInfoScreen(ServerPlayer player, ServerPlayer target, boolean showBackground) {
        PlayerInfo playerInfo = new PlayerInfo(
                target.getName().getString(),
                target.experienceLevel,
                CapabilitiesHandler.getPlayerJob(target),
                CapabilitiesHandler.getPlayerJobXp(target),
                CapabilitiesHandler.getPlayerTown(target),
                CapabilitiesHandler.getPlayerFriendListByUUID(target),
                // TODO: add player money counter and player online timer
                0,
                0
        );
        ServerPlayNetworking.send(player, new OpenPlayerInfoScreenS2CPacket(playerInfo, showBackground));
    }

    public static ChunkPos getPlayerChunkPos(Player player) {
        return new ChunkPos(player.blockPosition());
    }

    public static void sendToast(ServerPlayer player, String title, String subTitle) {
        ServerPlayNetworking.send(player, new SendSystemToastS2CPacket(title, subTitle));
    }


}
