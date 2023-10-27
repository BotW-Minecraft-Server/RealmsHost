package link.botwmcs.samchai.realmshost.network;

import link.botwmcs.samchai.realmshost.client.gui.ChooseJobScreen;
import link.botwmcs.samchai.realmshost.client.gui.ChooseTownScreen;
import link.botwmcs.samchai.realmshost.network.s2c.OpenChooseJobScreenS2CPacket;
import link.botwmcs.samchai.realmshost.network.s2c.OpenChooseTownScreenS2CPacket;
import link.botwmcs.samchai.realmshost.util.TownHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class S2CHandler {
    @Environment(EnvType.CLIENT)
    public static void register() {
        ClientPlayConnectionEvents.INIT.register((handler, client) -> {
            ClientPlayNetworking.registerReceiver(OpenChooseJobScreenS2CPacket.TYPE, S2CHandler::openChooseJobScreen);
            ClientPlayNetworking.registerReceiver(OpenChooseTownScreenS2CPacket.TYPE, S2CHandler::openChooseTownScreen);
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
}
