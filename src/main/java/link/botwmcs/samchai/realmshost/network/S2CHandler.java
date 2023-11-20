package link.botwmcs.samchai.realmshost.network;

import link.botwmcs.samchai.realmshost.client.gui.ChooseJobScreen;
import link.botwmcs.samchai.realmshost.client.gui.ChooseTownScreen;
import link.botwmcs.samchai.realmshost.client.gui.components.TrainBarMessage;
import link.botwmcs.samchai.realmshost.client.gui.PlayerInfoScreen;
import link.botwmcs.samchai.realmshost.network.s2c.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class S2CHandler {
    @Environment(EnvType.CLIENT)
    public static void register() {
        ClientPlayConnectionEvents.INIT.register((handler, client) -> {
            ClientPlayNetworking.registerReceiver(OpenChooseJobScreenS2CPacket.TYPE, S2CHandler::openChooseJobScreen);
            ClientPlayNetworking.registerReceiver(OpenChooseTownScreenS2CPacket.TYPE, S2CHandler::openChooseTownScreen);
            ClientPlayNetworking.registerReceiver(SendSystemToastS2CPacket.TYPE, S2CHandler::sendSystemToast);
            ClientPlayNetworking.registerReceiver(OpenPlayerInfoScreenS2CPacket.TYPE, S2CHandler::openPlayerInfoScreen);
            ClientPlayNetworking.registerReceiver(SendHudComponentS2CPacket.TYPE, S2CHandler::sendHudComponent);
        });
    }

    @Environment(EnvType.CLIENT)
    private static void openChooseJobScreen(OpenChooseJobScreenS2CPacket packet, Player player, PacketSender sender) {
        Minecraft.getInstance().setScreen(new ChooseJobScreen(Component.translatable("gui.botwmcs.realmshost.chooseJobScreen.title"), player, packet.showBackground()));
    }
    @Environment(EnvType.CLIENT)
    private static void openChooseTownScreen(OpenChooseTownScreenS2CPacket packet, Player player, PacketSender sender) {
        Minecraft.getInstance().setScreen(new ChooseTownScreen(Component.translatable("gui.botwmcs.realmshost.chooseTownScreen.title"), packet.showBackground()));
    }
    @Environment(EnvType.CLIENT)
    private static void sendSystemToast(SendSystemToastS2CPacket packet, Player player, PacketSender sender) {
        SystemToast toast = new SystemToast(SystemToast.SystemToastIds.TUTORIAL_HINT, Component.nullToEmpty(packet.title()), Component.nullToEmpty(packet.title()));
        Minecraft.getInstance().getToasts().addToast(toast);
    }
    @Environment(EnvType.CLIENT)
    private static void openPlayerInfoScreen(OpenPlayerInfoScreenS2CPacket packet, Player player, PacketSender sender) {
        Minecraft.getInstance().setScreen(new PlayerInfoScreen(Component.translatable("gui.botwmcs.realmshost.playerInfoScreen.title"), packet.playerInfo(), packet.showBackground()));
    }
    @Environment(EnvType.CLIENT)
    private static void sendHudComponent(SendHudComponentS2CPacket packet, Player player, PacketSender sender) {
        Minecraft.getInstance().execute(() -> {
            TrainBarMessage.getInstance().onShowHUDMessage(Component.nullToEmpty(packet.component()), packet.stayTime() * 20 + 100);
//            new java.util.Timer().schedule(new java.util.TimerTask() {
//                @Override
//                public void run() {
//                    PlayerHUD.hideComponent();
//                }
//            }, packet.stayTime() * 1000L);
        });
    }
}
