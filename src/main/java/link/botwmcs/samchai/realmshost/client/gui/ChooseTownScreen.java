package link.botwmcs.samchai.realmshost.client.gui;

import link.botwmcs.samchai.realmshost.network.c2s.ChooseTownC2SPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;


public class ChooseTownScreen extends Screen {
    private final boolean showBackground;
    private final Player localPlayer;
    private final ResourceLocation recipeBookGuiTextures = new ResourceLocation("textures/gui/recipe_book.png");
    public ChooseTownScreen(Component component, Player player, boolean showBackground) {
        super(component);
        this.showBackground = showBackground;
        this.localPlayer = player;
    }

    @Override
    protected void init() {
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
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(guiGraphics, mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
    }
    @Override
    public void onClose() {
        super.onClose();
    }
    @Override
    public void tick() {
        super.tick();
    }
    @Override
    public void removed() {
        super.removed();
    }

    private void renderBackgroundInBlock(GuiGraphics guiGraphics, ResourceLocation resourceLocation) {
        guiGraphics.setColor(0.25F, 0.25F, 0.25F, 1.0F);
        guiGraphics.blit(resourceLocation, 0, 0, 0, 0.0F, 0.0F, this.width, this.height, 32, 32);
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
    private void renderComponents(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        Font font = Minecraft.getInstance().font;
        int centeredX = this.width / 2;
        int centeredY = this.height / 2;
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.drawCenteredString(font, Component.translatable("gui.botwmcs.realmshost.chooseTownScreen.title"), centeredX, centeredY - 100, 0xFFFFFF);
        guiGraphics.setColor(0.8F, 1.0F, 1.0F, 1.0F);
        guiGraphics.blit(recipeBookGuiTextures, centeredX - 147 / 2, centeredY - 166 / 2, 1, 1, 147, 166);
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
    private void renderButtons() {
        int buttonStartX = this.width / 2 - 147 / 2 + 8;
        int buttonStartY = this.height / 2 - 166 / 2 + 8;
        final AbstractWidget defaultTownButton = addRenderableWidget(new ColorButton(buttonStartX, buttonStartY, 147 - 16, 20, Component.translatable("gui.botwmcs.realmshost.defaultTown"), 0x00FFFFFF, sender -> {
            setPlayerTown("default");
        }));
        defaultTownButton.setTooltip(Tooltip.create(Component.translatable("gui.botwmcs.realmshost.defaultTown.tooltip")));
    }
    private void setPlayerTown(String townName) {
        ClientPlayNetworking.send(new ChooseTownC2SPacket(townName));
        super.onClose();
    }

}
