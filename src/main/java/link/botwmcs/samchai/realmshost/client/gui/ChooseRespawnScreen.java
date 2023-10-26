package link.botwmcs.samchai.realmshost.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class ChooseRespawnScreen extends Screen {
    private static final ResourceLocation VILLAGER_TEXTURE = new ResourceLocation("textures/gui/container/villager.png");
    private static final ResourceLocation SCROLLER_SPRITE = new ResourceLocation("container/villager/scroller");
    private static final ResourceLocation SCROLLER_DISABLED_SPRITE = new ResourceLocation("container/villager/scroller_disabled");
    private final boolean showBackground;
    private final Player player;
    public ChooseRespawnScreen(Component component, Player player, boolean showBackground) {
        super(component);
        this.player = player;
        this.showBackground = showBackground;
    }
    protected void init() {
        super.init();
    }
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float f) {
        if (this.showBackground) {
            this.renderDirtBackground(guiGraphics);
        } else {
            this.renderTransparentBackground(guiGraphics);
        }
        // Render GUI graphics components
        int centeredX = this.width / 2 - 108 / 2;
        int centeredY = this.height / 6;
        int xOffset = centeredX + 100;
        // Render scroll background
        guiGraphics.blit(VILLAGER_TEXTURE, xOffset + 10, centeredY, 0, 180, 0.0F, 107, 256, 512, 256);
        guiGraphics.blit(VILLAGER_TEXTURE, xOffset, centeredY, 0, 0.0F, 0.0F, 101, 256, 512, 256);
    }
    public void renderTransparentBackground(GuiGraphics guiGraphics) {
        guiGraphics.fillGradient(0, 0, this.width, this.height, -5, 1678774288, -2112876528);
    }
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        super.render(guiGraphics, mouseX, mouseY, delta);
    }
}
