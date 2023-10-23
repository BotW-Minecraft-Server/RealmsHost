package link.botwmcs.samchai.realmshost.event.player;

import link.botwmcs.samchai.realmshost.capability.CcaHandler;
import link.botwmcs.samchai.realmshost.util.PlayerUtilities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class WorldJoinEvent {
    public static void onLoggedIn(Level world, Player player, BlockPos blockPos) {
        if (world.isClientSide) {
            player.sendSystemMessage(Component.translatable("chat.botwmcs.realmshost.inClientMode"));

            return;
        }
        if (CcaHandler.ACCOUNT_COMPONENT_KEY.get(player).isPlayerFirstJoinServer()) {
            player.sendSystemMessage(net.minecraft.network.chat.Component.nullToEmpty("Welcome to LTSX!"));
            player.sendSystemMessage(Component.nullToEmpty(CcaHandler.ACCOUNT_COMPONENT_KEY.get(player).isPlayerFirstJoinServer().toString()));
            // Open ChooseJobScreen
            PlayerUtilities.openJobChooseScreen((ServerPlayer) player, true);

        } else {
            player.sendSystemMessage(net.minecraft.network.chat.Component.nullToEmpty("Welcome back to LTSX!"));
            player.sendSystemMessage(Component.nullToEmpty(CcaHandler.ACCOUNT_COMPONENT_KEY.get(player).isPlayerFirstJoinServer().toString()));
            PlayerUtilities.openJobChooseScreen((ServerPlayer) player, true);
        }
    }
}
