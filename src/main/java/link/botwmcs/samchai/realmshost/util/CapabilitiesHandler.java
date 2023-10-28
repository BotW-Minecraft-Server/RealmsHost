package link.botwmcs.samchai.realmshost.util;

import link.botwmcs.samchai.realmshost.RealmsHost;
import link.botwmcs.samchai.realmshost.capability.AccountHandler;
import link.botwmcs.samchai.realmshost.capability.DeathCounter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.time.Instant;

public class CapabilitiesHandler {
    public static String getPlayerJob(Player player) {
        return AccountHandler.ACCOUNT_COMPONENT_KEY.get(player).getPlayerJob();
    }
    public static String getPlayerTown(Player player) {
        return AccountHandler.ACCOUNT_COMPONENT_KEY.get(player).getPlayerTown();
    }
    public static Integer getPlayerJobXp(Player player) {
        return AccountHandler.ACCOUNT_COMPONENT_KEY.get(player).getPlayerJobXp();
    }
    public static Boolean isPlayerFirstJoinServer(Player player) {
        return AccountHandler.ACCOUNT_COMPONENT_KEY.get(player).isPlayerFirstJoinServer();
    }
    public static void setPlayerJob(Player player, String playerJob) {
        AccountHandler.ACCOUNT_COMPONENT_KEY.get(player).setPlayerJob(playerJob);
    }
    public static void setPlayerTown(Player player, String playerTown) {
        AccountHandler.ACCOUNT_COMPONENT_KEY.get(player).setPlayerTown(playerTown);
    }
    public static void setPlayerJobXp(Player player, Integer playerJobXp) {
        AccountHandler.ACCOUNT_COMPONENT_KEY.get(player).setPlayerJobXp(playerJobXp);
    }
    public static void setPlayerJobAsFarmer(Player player) {
        AccountHandler.ACCOUNT_COMPONENT_KEY.get(player).setPlayerJob("farmer");
    }
    public static void setPlayerJobAsMiner(Player player) {
        AccountHandler.ACCOUNT_COMPONENT_KEY.get(player).setPlayerJob("miner");
    }
    public static void setPlayerJobAsKnight(Player player) {
        AccountHandler.ACCOUNT_COMPONENT_KEY.get(player).setPlayerJob("knight");
    }
    public static void setPlayerJobAsDefault(Player player) {
        AccountHandler.ACCOUNT_COMPONENT_KEY.get(player).setPlayerJob("default");
    }
    public static void addDeathCounter(Player player, Level deathLevel, BlockPos deathPos) {
        long currentTime = Instant.now().toEpochMilli();
        AccountHandler.ACCOUNT_COMPONENT_KEY.get(player).addDeathCounter(new DeathCounter(deathLevel.dimension(), deathPos, currentTime));
    }


}
