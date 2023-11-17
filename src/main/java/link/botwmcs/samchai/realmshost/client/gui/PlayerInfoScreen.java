package link.botwmcs.samchai.realmshost.client.gui;

import link.botwmcs.samchai.realmshost.capability.PlayerInfo;
import link.botwmcs.samchai.realmshost.client.gui.components.ColorButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class PlayerInfoScreen extends Screen {
    public final PlayerInfo playerInfo;
    public final boolean showBackground;
    private static final ResourceLocation VILLAGER_LOCATION = new ResourceLocation("textures/gui/container/villager2.png");
    public PlayerInfoScreen(Component component, PlayerInfo playerInfo, boolean showBackground) {
        super(component);
        this.playerInfo = playerInfo;
        this.showBackground = showBackground;
    }

    protected void init() {
        super.init();

    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        if (this.showBackground) {
            this.renderBackground(guiGraphics, mouseX, mouseY, delta);
        }
        super.render(guiGraphics, mouseX, mouseY, delta);
    }

    private void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float f) {
        if (this.showBackground) {
            this.renderDirtBackground(guiGraphics);
        } else {
            this.renderTransparentBackground(guiGraphics);
        }

        int centeredX = this.width / 2;
        int centeredY = this.height / 2;

        int i = (this.width - 276) / 2;
        int j = (this.height - 107) / 2;
        guiGraphics.blit(VILLAGER_LOCATION, i, j, 0, 0.0F, 0.0F, 276, 107, 512, 256);

    }
    private void renderTransparentBackground(GuiGraphics guiGraphics) {
        guiGraphics.fillGradient(0, 0, this.width, this.height, -5, 1678774288, -2112876528);
    }


}
