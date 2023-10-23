package link.botwmcs.samchai.realmshost.util;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import link.botwmcs.samchai.realmshost.capability.CcaHandler;
import link.botwmcs.samchai.realmshost.network.s2c.OpenChooseJobScreenS2CPacket;
import link.botwmcs.samchai.realmshost.network.s2c.OpenChooseTownScreenS2CPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;

public class PlayerUtilities {
    public static boolean isPlayerFirstJoinServer(ServerPlayer player) {
        return CcaHandler.ACCOUNT_COMPONENT_KEY.get(player).isPlayerFirstJoinServer();
    }
    public static void openJobChooseScreen(ServerPlayer player, boolean showBackground) {
        ServerPlayNetworking.send(player, new OpenChooseJobScreenS2CPacket(showBackground));
    }
    public static void openTownChooseScreen(ServerPlayer player, boolean showBackground) {
        ServerPlayNetworking.send(player, new OpenChooseTownScreenS2CPacket(showBackground));
    }
}
