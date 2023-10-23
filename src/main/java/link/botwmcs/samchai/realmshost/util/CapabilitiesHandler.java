package link.botwmcs.samchai.realmshost.util;

import link.botwmcs.samchai.realmshost.capability.CcaHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class CapabilitiesHandler {
    public static String getPlayerJob(Player player) {
        return CcaHandler.ACCOUNT_COMPONENT_KEY.get(player).getPlayerJob();
    }
    public static String getPlayerTown(Player player) {
        return CcaHandler.ACCOUNT_COMPONENT_KEY.get(player).getPlayerTown();
    }
    public static Integer getPlayerJobXp(Player player) {
        return CcaHandler.ACCOUNT_COMPONENT_KEY.get(player).getPlayerJobXp();
    }
    public static Boolean isPlayerFirstJoinServer(Player player) {
        return CcaHandler.ACCOUNT_COMPONENT_KEY.get(player).isPlayerFirstJoinServer();
    }

    public static void setPlayerJob(Player player, String playerJob) {
        CcaHandler.ACCOUNT_COMPONENT_KEY.get(player).setPlayerJob(playerJob);
    }
    public static void setPlayerTown(Player player, String playerTown) {
        CcaHandler.ACCOUNT_COMPONENT_KEY.get(player).setPlayerTown(playerTown);
    }
    public static void setPlayerJobXp(Player player, Integer playerJobXp) {
        CcaHandler.ACCOUNT_COMPONENT_KEY.get(player).setPlayerJobXp(playerJobXp);
    }

    public static void setPlayerJobAsFarmer(Player player) {
        CcaHandler.ACCOUNT_COMPONENT_KEY.get(player).setPlayerJob("farmer");
    }
    public static void setPlayerJobAsMiner(Player player) {
        CcaHandler.ACCOUNT_COMPONENT_KEY.get(player).setPlayerJob("miner");
    }
    public static void setPlayerJobAsKnight(Player player) {
        CcaHandler.ACCOUNT_COMPONENT_KEY.get(player).setPlayerJob("knight");
    }
    public static void setPlayerJobAsDefault(Player player) {
        CcaHandler.ACCOUNT_COMPONENT_KEY.get(player).setPlayerJob("default");
    }
}
