package link.botwmcs.samchai.realmshost.event.player;

import link.botwmcs.samchai.realmshost.util.CapabilitiesHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class PlayerDeathEvent {
    public static void onPlayerDeath(Level world, Player player, BlockPos deathPos) {
        CapabilitiesHandler.addDeathCounter(player, world, deathPos);
    }
}
