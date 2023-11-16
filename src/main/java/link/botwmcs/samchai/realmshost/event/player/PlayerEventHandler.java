package link.botwmcs.samchai.realmshost.event.player;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

import static link.botwmcs.samchai.realmshost.event.player.UsePlayerEvent.onUsePlayer;

public class PlayerEventHandler {
    private PlayerEventHandler() {
    }

    public static final Event<PlayerLoggedIn> PLAYER_LOGGED_IN_EVENT = EventFactory.createArrayBacked(PlayerLoggedIn.class, callbacks -> (world, player, blockPos) -> {
        for (PlayerLoggedIn callback : callbacks) {
            callback.onPlayerLoggedIn(world, player, blockPos);
        }
    });
    @FunctionalInterface
    public interface PlayerLoggedIn {
        void onPlayerLoggedIn(Level world, Player player, BlockPos blockPos);
    }

    public static final Event<PlayerLoggedOut> PLAYER_LOGGED_OUT_EVENT = EventFactory.createArrayBacked(PlayerLoggedOut.class, callbacks -> (world, player, blockPos) -> {
        for (PlayerLoggedOut callback : callbacks) {
            callback.onPlayerLoggedOut(world, player, blockPos);
        }
    });
    @FunctionalInterface
    public interface PlayerLoggedOut {
        void onPlayerLoggedOut(Level world, Player player, BlockPos blockPos);
    }

    public static final Event<PlayerDeath> PLAYER_DEATH_EVENT = EventFactory.createArrayBacked(PlayerDeath.class, callbacks -> (world, player, blockPos) -> {
        for (PlayerDeath callback : callbacks) {
            callback.onPlayerDeath(world, player, blockPos);
        }
    });
    @FunctionalInterface
    public interface PlayerDeath {
        void onPlayerDeath(Level world, Player player, BlockPos blockPos);
    }
}
