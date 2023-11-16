package link.botwmcs.samchai.realmshost.util;

import link.botwmcs.samchai.realmshost.capability.AccountHandler;
import link.botwmcs.samchai.realmshost.capability.DeathCounter;
import link.botwmcs.samchai.realmshost.capability.Friend;
import link.botwmcs.samchai.realmshost.capability.Home;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    public static List<DeathCounter> getPlayerDeathCounterList(Player player) {
        return AccountHandler.ACCOUNT_COMPONENT_KEY.get(player).getDeathCounterList();
    }
    public static List<Home> getPlayerHomeList(Player player) {
        return AccountHandler.ACCOUNT_COMPONENT_KEY.get(player).getHomeList();
    }
    public static List<Friend> getPlayerFriendList(Player player) {
        List<Friend> friendList = AccountHandler.ACCOUNT_COMPONENT_KEY.get(player).getFriendList();
        for (Friend friend : friendList) {
            // TODO: support single-player
            if (friend.isOnline && player.getServer() == null) {
                Player friendPlayer = player.getServer().getPlayerList().getPlayer(friend.friendUUID);
                if (friendPlayer == null) {
                    friend.isOnline = false;
                }
            }
        }
        return AccountHandler.ACCOUNT_COMPONENT_KEY.get(player).getFriendList();
    }
    public static List<UUID> getPlayerFriendListByUUID(Player player) {
        List<Friend> friendList = getPlayerFriendList(player);
        List<UUID> friendListUUID = new ArrayList<>();
        for (Friend friend : friendList) {
            friendListUUID.add(friend.friendUUID);
        }
        return friendListUUID;
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
        List<DeathCounter> counterList = getPlayerDeathCounterList(player);
        counterList.add(new DeathCounter(deathLevel.dimension(), deathPos, currentTime));
        AccountHandler.ACCOUNT_COMPONENT_KEY.get(player).setDeathCounterList(counterList);
    }
    public static void addHome(Player player, Level homeLevel, BlockPos homePos, String homeName) {
        List<Home> homeList = getPlayerHomeList(player);
        homeList.add(new Home(homeLevel.dimension(), homePos, homeName));
        AccountHandler.ACCOUNT_COMPONENT_KEY.get(player).setHomeList(homeList);
    }
    public static void removeHome(Player player, String homeName) {
        List<Home> homeList = getPlayerHomeList(player);
        homeList.removeIf(home -> home.homeName.equals(homeName));
        AccountHandler.ACCOUNT_COMPONENT_KEY.get(player).setHomeList(homeList);
    }
    public static void addFriend(Player player, Player friendPlayer) {
        List<Friend> friendList = getPlayerFriendList(player);
        friendList.add(new Friend(friendPlayer.getUUID(), true));
    }
    public static void removeFriend(Player player, UUID frendUUID) {
        List<Friend> friendList = getPlayerFriendList(player);
        friendList.removeIf(friend -> friend.friendUUID.equals(frendUUID));
        AccountHandler.ACCOUNT_COMPONENT_KEY.get(player).setFriendList(friendList);
    }
}
