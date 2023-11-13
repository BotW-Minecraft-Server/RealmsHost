package link.botwmcs.samchai.realmshost.mixin;

import com.mojang.authlib.GameProfile;
import link.botwmcs.samchai.realmshost.event.player.PlayerEventHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ServerPlayer.class, priority = 1001)
public abstract class ServerPlayerMixin extends Player {
    public ServerPlayerMixin(Level level, BlockPos blockPos, float f, GameProfile gameProfile) {
        super(level, blockPos, f, gameProfile);
    }

    @Shadow @Final public MinecraftServer server;

    @Inject(method = "die", at = @At("HEAD"))
    protected void injectPlayerDeathEvent(CallbackInfo ci) {
        PlayerEventHandler.PLAYER_DEATH_EVENT.invoker().onPlayerDeath(this.level(), this, this.blockPosition());
    }
}
