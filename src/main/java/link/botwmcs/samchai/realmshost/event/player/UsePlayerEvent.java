package link.botwmcs.samchai.realmshost.event.player;

import link.botwmcs.samchai.realmshost.util.PlayerUtilities;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class UsePlayerEvent {
    public static InteractionResult onUsePlayer(Player invokePlayer, Level level, InteractionHand interactionHand, Entity targetPlayer, EntityHitResult entityHitResult) {
        if (targetPlayer instanceof Player && !level.isClientSide()) {
            // TODO: Open PlayerInfoScreen
            PlayerUtilities.openPlayerInfoScreen((ServerPlayer) invokePlayer, (ServerPlayer) targetPlayer, true);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
