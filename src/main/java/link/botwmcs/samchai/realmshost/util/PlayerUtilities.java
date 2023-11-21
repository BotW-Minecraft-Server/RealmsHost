package link.botwmcs.samchai.realmshost.util;

import link.botwmcs.samchai.realmshost.RealmsHost;
import link.botwmcs.samchai.realmshost.capability.Account;
import link.botwmcs.samchai.realmshost.capability.AccountHandler;
import link.botwmcs.samchai.realmshost.capability.DeathCounter;
import link.botwmcs.samchai.realmshost.capability.PlayerInfo;
import link.botwmcs.samchai.realmshost.network.s2c.*;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

    public static void sendTrainBarHud(ServerPlayer serverPlayer, String component, int stayTime) {
        ServerPlayNetworking.send(serverPlayer, new SendHudComponentS2CPacket(component, stayTime));
    }

    public static void sendTrainBarQueue(ServerPlayer serverPlayer, List<String> list, int stayTime) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        for (int i = 0; i < list.size(); i++) {
            String component = list.get(i);
            int delay = stayTime * i;
            scheduler.schedule(() -> sendTrainBarHud(serverPlayer, component, stayTime + 100), delay, TimeUnit.SECONDS);
        }
        scheduler.shutdown();
    }

    public static void sendBossBarHud(ServerPlayer serverPlayer, String component, int stayTime) {
        ServerPlayNetworking.send(serverPlayer, new SendBossBarHudComponentS2CPacket(component, stayTime));
    }

    public static void sendBossBarHudQueue(ServerPlayer serverPlayer, List<String> list, int stayTime) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        for (int i = 0; i < list.size(); i++) {
            String component = list.get(i);
            int delay = stayTime * i;
            scheduler.schedule(() -> sendBossBarHud(serverPlayer, component, stayTime + 100), delay, TimeUnit.SECONDS);
        }
        scheduler.shutdown();
    }

    public static void sendBasicInfoHud(ServerPlayer serverPlayer) {
        List<String> list = new ArrayList<>();
        // todo: mail system
        int mailCount = 10;
        // todo: temperature system
        list.add(Component.translatable("info.botwmcs.realmshost.welcome").getString());
        list.add(Component.translatable("info.botwmcs.realmshost.nowDate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/HH:mm"))).getString());
        if (mailCount > 0) {
            list.add(Component.translatable("info.botwmcs.realmshost.mail", mailCount).getString());
        }
        list.add(Component.translatable("info.botwmcs.realmshost.temp", "26 | 36.5").getString());
        list.add(Component.translatable("info.botwmcs.realmshost.havefun").getString());

        sendTrainBarQueue(serverPlayer, list, 3);
    }


}
