package link.botwmcs.samchai.realmshost.event.player;

import link.botwmcs.samchai.realmshost.util.PlayerUtilities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class PlayerDeathEvent {
    public static void onPlayerDeath(Level world, Player player, BlockPos deathPos) {
        PlayerUtilities.addDeathCounter(player, world, deathPos);
    }
}
