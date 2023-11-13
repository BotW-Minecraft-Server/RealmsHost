package link.botwmcs.samchai.realmshost.mixin;

import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {
    @Inject(method = "getServerBrand", at = @At("HEAD"), cancellable = true)
    protected void modifyServerBrand(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue("LTSX RHX Hooker <- FabricImpl");
    }
}
