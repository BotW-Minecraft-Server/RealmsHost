package link.botwmcs.samchai.realmshost.event.player;

import link.botwmcs.samchai.realmshost.capability.CcaHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class WorldLeaveEvent {
    public static void onLoggedOut(Level world, Player player, BlockPos blockPos) {
        if (world.isClientSide) {
            return;
        }
        if (CcaHandler.ACCOUNT_COMPONENT_KEY.get(player).isPlayerFirstJoinServer()) {
            CcaHandler.ACCOUNT_COMPONENT_KEY.get(player).setPlayerFirstJoinServer(false);
        }
    }
}
