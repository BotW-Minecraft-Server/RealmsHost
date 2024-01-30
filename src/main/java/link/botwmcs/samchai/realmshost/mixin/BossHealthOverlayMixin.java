package link.botwmcs.samchai.realmshost.mixin;

import link.botwmcs.samchai.realmshost.client.gui.components.BossBarMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.BossEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Mixin(BossHealthOverlay.class)
public abstract class BossHealthOverlayMixin {
    @Shadow @Final private Map<UUID, LerpingBossEvent> events;

    @Shadow protected abstract void drawBar(GuiGraphics guiGraphics, int x, int y, BossEvent bossEvent);

    @Shadow @Final private Minecraft minecraft;

    /**
     * @author Sam_Chai
     * @reason Add a check if BossBarHud is shows
     */
    @Overwrite
    public void render(GuiGraphics guiGraphics) {
        if (!this.events.isEmpty()) {
            int i = guiGraphics.guiWidth();
            int j = 12;
            if (BossBarMessage.isEnabled()) {
                j += 25;
            }

            Iterator var4 = this.events.values().iterator();

            while(var4.hasNext()) {
                LerpingBossEvent lerpingBossEvent = (LerpingBossEvent)var4.next();
                int k = i / 2 - 91;
                this.drawBar(guiGraphics, k, j, lerpingBossEvent);
                Component component = lerpingBossEvent.getName();
                int m = this.minecraft.font.width(component);
                int n = i / 2 - m / 2;
                int o = j - 9;
                guiGraphics.drawString(this.minecraft.font, component, n, o, 16777215);
                Objects.requireNonNull(this.minecraft.font);
                j += 10 + 9;
                if (j >= guiGraphics.guiHeight() / 3) {
                    break;
                }
            }

        }
    }

}
