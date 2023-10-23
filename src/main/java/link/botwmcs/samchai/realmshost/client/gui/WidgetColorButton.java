package link.botwmcs.samchai.realmshost.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.function.Supplier;

// From: common/src/main/java/cn/zbx1425/worldcomment/gui/WidgetColorButton.java
public class WidgetColorButton extends Button {

    int color;
    private static final WidgetSprites SPRITES = new WidgetSprites(new ResourceLocation("widget/button"), new ResourceLocation("widget/button_disabled"), new ResourceLocation("widget/button_highlighted"));

    public WidgetColorButton(int width, int height, int buttonWidth, int buttonHeight, Component component, int color, OnPress onPress) {
        super(width, height, buttonWidth, buttonHeight, component, onPress, Supplier::get);
        this.color = color;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        if (this.active) {
            guiGraphics.setColor(((color >> 16) & 0xFF) / 255f, ((color >> 8) & 0xFF) / 255f,
                    (color & 0xFF) / 255f, this.alpha);
        } else {
            guiGraphics.setColor(1.0f, 1.0f, 1.0f, this.alpha);
        }
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        guiGraphics.blit(SPRITES.get(this.active, this.isHoveredOrFocused()), this.getX(), this.getY(), this.getWidth(), this.getHeight(), 20, 4, 200, 20, 0, this.getTextureY());
        guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        int i = this.active ? 0xFFFFFF : 0xA0A0A0;
        this.renderString(guiGraphics, minecraft.font, i | Mth.ceil(this.alpha * 255.0f) << 24);
    }

    private int getTextureY() {
        int i = 1;
        if (!this.active) {
            i = 0;
        } else if (this.isHoveredOrFocused()) {
            i = 2;
        }
        return 46 + i * 20;
    }
}