package link.botwmcs.samchai.realmshost.event.player;

import link.botwmcs.samchai.realmshost.capability.AccountHandler;
import link.botwmcs.samchai.realmshost.config.ServerConfig;
import link.botwmcs.samchai.realmshost.util.PlayerUtilities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class WorldJoinEvent {
    public static void onLoggedIn(Level world, Player player, BlockPos blockPos) {
        if (player.getCommandSenderWorld().isClientSide()) {
            player.sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.inClientMode"));
            return;
        }
        if (AccountHandler.ACCOUNT_COMPONENT_KEY.get(player).isPlayerFirstJoinServer()) {
            player.sendSystemMessage(net.minecraft.network.chat.Component.nullToEmpty("Welcome to LTSX!"));
            // Open ChooseJobScreen
            if (ServerConfig.CONFIG.enableFirstJoinServerOpenMenu.get()) {
                PlayerUtilities.openJobChooseScreen((ServerPlayer) player, true);
            }
        } else {
            player.sendSystemMessage(net.minecraft.network.chat.Component.nullToEmpty("Welcome back to LTSX!"));
        }

        // broadcast basic info
        PlayerUtilities.sendBasicInfoHud((ServerPlayer) player);
    }
}
