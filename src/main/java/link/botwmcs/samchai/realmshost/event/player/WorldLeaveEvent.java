package link.botwmcs.samchai.realmshost.event.player;

import link.botwmcs.samchai.realmshost.capability.AccountHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class WorldLeaveEvent {
    public static void onLoggedOut(Level world, Player player, BlockPos blockPos) {
        if (world.isClientSide) {
            return;
        }
        if (AccountHandler.ACCOUNT_COMPONENT_KEY.get(player).isPlayerFirstJoinServer()) {
            AccountHandler.ACCOUNT_COMPONENT_KEY.get(player).setPlayerFirstJoinServer(false);
        }
    }
}
