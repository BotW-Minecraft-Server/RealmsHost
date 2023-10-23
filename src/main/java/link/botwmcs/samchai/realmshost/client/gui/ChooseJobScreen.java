package link.botwmcs.samchai.realmshost.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.awt.*;
import java.util.List;

public class ChooseJobScreen extends Screen {
    private List<Component> tooltip;
    private final boolean showBackground;
    private final Player localPlayer;
    private final ResourceLocation recipeBookGuiTextures = new ResourceLocation("textures/gui/recipe_book.png");

    public ChooseJobScreen(Component component, Player player, boolean showBackground) {
        super(component);
        this.localPlayer = player;
        this.showBackground = showBackground;
    }
    @Override
    protected void init() {
        Minecraft minecraft = Minecraft.getInstance();
        super.init();
        renderButtons();
    }
    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float f) {
        if (this.showBackground) {
            this.renderDirtBackground(guiGraphics);
        } else {
            this.renderTransparentBackground(guiGraphics);
        }
        // Render GUI graphics components
        renderComponents(guiGraphics, mouseX, mouseY, f);
    }
    @Override
    public void renderTransparentBackground(GuiGraphics guiGraphics) {
        guiGraphics.fillGradient(0, 0, this.width, this.height, -5, 1678774288, -2112876528);
    }
    @Override
    public void renderDirtBackground(GuiGraphics guiGraphics) {
        this.renderBackgroundInBlock(guiGraphics, new ResourceLocation("textures/block/dirt.png"));
    }
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float f) {
        this.tooltip = null;
        this.renderBackground(guiGraphics, mouseX, mouseY, f);
        if (this.tooltip != null) {
            guiGraphics.renderComponentTooltip(this.font, this.tooltip, mouseX, mouseY);
        }
//        renderComponents(guiGraphics, mouseX, mouseY, f);
        super.render(guiGraphics, mouseX, mouseY, f);
    }
    @Override
    public void tick() {
        super.tick();
    }
    @Override
    public void removed() {
        super.removed();
    }
    @Override
    public void onClose() {
    }

    private void renderBackgroundInBlock(GuiGraphics guiGraphics, ResourceLocation resourceLocation) {
        guiGraphics.setColor(0.25F, 0.25F, 0.25F, 1.0F);
        guiGraphics.blit(resourceLocation, 0, 0, 0, 0.0F, 0.0F, this.width, this.height, 32, 32);
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
    private void renderComponents(GuiGraphics guiGraphics, int mouseX, int mouseY, float f) {
        Font font = Minecraft.getInstance().font;
        int centeredX = this.width / 2;
        int centeredY = this.height / 2;
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.drawCenteredString(font, "Choose your job", centeredX, centeredY - 100, 0xFFFFFF);
        guiGraphics.blit(recipeBookGuiTextures, centeredX - 147 / 2, centeredY - 166 / 2, 0, 0, 147, 166);

    }
    private void renderButtons() {
        addRenderableWidget(new WidgetColorButton(10, 10, 150, 20, Component.literal("X"), 0xFFEF9A9A, sender -> {
            super.onClose();
        }));
    }
    private void setToolTip(List<Component> list) {
        this.tooltip = list;
    }
}
