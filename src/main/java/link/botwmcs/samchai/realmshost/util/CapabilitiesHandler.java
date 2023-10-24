package link.botwmcs.samchai.realmshost.util;

import link.botwmcs.samchai.realmshost.capability.AccountHandler;
import link.botwmcs.samchai.realmshost.capability.town.Town;
import link.botwmcs.samchai.realmshost.capability.town.TownCompound;
import link.botwmcs.samchai.realmshost.capability.town.TownCompoundHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.Objects;

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

    public static void createTown(Level world, Player owner, String townName, String townComment, boolean isPublic, boolean isOpen, boolean isStared, int townLevel, int townFunds, BlockPos townSpawn, BlockPos townHall, BlockPos townMarket, BlockPos townBank, BlockPos townJobBoard, BlockPos townYard) {
        Town town = new Town(townName, townComment, owner.getUUID(), isPublic, isOpen, isStared, townLevel, townFunds, townSpawn, townHall, townMarket, townBank, townJobBoard, townYard);
        town.addResident((ServerPlayer) owner);
        town.addClaimedChunk(PlayerUtilities.getPlayerChunkPos((ServerPlayer) owner));
        world.getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).addTown(town);
        if (!world.isClientSide()) {
            ServerUtilities.saveAll(Objects.requireNonNull(world.getServer()));
        }
    }
    public static void createTown(Level world, Player owner, String townName) {
        createTown(world, owner, townName, "A default town comment", true, true, false, 0, 0, owner.getOnPos(), owner.getOnPos(), owner.getOnPos(), owner.getOnPos(), owner.getOnPos(), owner.getOnPos());
    }
}
