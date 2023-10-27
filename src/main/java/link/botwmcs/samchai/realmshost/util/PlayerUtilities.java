package link.botwmcs.samchai.realmshost.util;

import link.botwmcs.samchai.realmshost.capability.AccountHandler;
import link.botwmcs.samchai.realmshost.capability.town.TownCompoundHandler;
import link.botwmcs.samchai.realmshost.network.s2c.OpenChooseJobScreenS2CPacket;
import link.botwmcs.samchai.realmshost.network.s2c.OpenChooseTownScreenS2CPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;

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
    public static ChunkPos getPlayerChunkPos(Player player) {
        return new ChunkPos(player.blockPosition());
    }
}
