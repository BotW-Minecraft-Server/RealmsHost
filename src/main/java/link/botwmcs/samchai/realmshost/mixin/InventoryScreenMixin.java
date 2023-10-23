package link.botwmcs.samchai.realmshost.mixin;

import link.botwmcs.samchai.realmshost.util.CapabilitiesHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = InventoryScreen.class, priority = 1001)
public class InventoryScreenMixin {
    @Inject(method = "render", at = @At("TAIL"))
    protected void injectPlayerJobComponent(GuiGraphics guiGraphics, int i, int j, float f, CallbackInfo ci) {
        renderPlayerJobComponent(guiGraphics, i, j, f);
    }

    @Unique
    private static void renderPlayerJobComponent(GuiGraphics guiGraphics, int i, int j, float f) {
        Minecraft minecraft = Minecraft.getInstance();
        guiGraphics.pose().pushPose();

        String jobs = "Jobs: " + CapabilitiesHandler.getPlayerJob(minecraft.player);
        guiGraphics.drawCenteredString(minecraft.font, jobs, guiGraphics.guiWidth() / 2, guiGraphics.guiHeight() / 2 - 100, 0xFFFFFF);
    }
}
