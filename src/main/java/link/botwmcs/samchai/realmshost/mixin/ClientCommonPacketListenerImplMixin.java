package link.botwmcs.samchai.realmshost.mixin;

import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ClientCommonPacketListenerImpl.class, priority = 1001)
public class ClientCommonPacketListenerImplMixin {
    @Shadow @Nullable protected String serverBrand;

    @Inject(method = "handleCustomPayload", at = @At("TAIL"))
    protected void modifyServerBrand(CallbackInfo ci) {
        if (true) {
            this.serverBrand = "LTSX RHX Hooker <- FabricImpl";
        }
    }
}
