package link.botwmcs.samchai.realmshost.client.gui;


import com.mojang.blaze3d.systems.RenderSystem;
import link.botwmcs.samchai.realmshost.capability.town.Town;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class TownButton extends Button {
    final Town town;
    final int index;
    int color;
    private static final WidgetSprites SPRITES = new WidgetSprites(new ResourceLocation("widget/button"), new ResourceLocation("widget/button_disabled"), new ResourceLocation("widget/button_highlighted"));

    public TownButton(int x, int y, int buttonWidth, int buttonHeight, Town town, int index, Component component, OnPress onPress) {
        super(x, y, buttonWidth, buttonHeight, component, onPress, Supplier::get);
        this.town = town;
        this.index = index;
        if (this.town.isStared) {
            this.color = 0xFFEEC538;
        } else {
            this.color = 0x00FFFFFF;
        }
    }
    public Town getTown() {
        return town;
    }
    public int getIndex() {
        return index;
    }
    public String getTownName() {
        return town.townName;
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
        guiGraphics.blitSprite(SPRITES.get(this.active, this.isHoveredOrFocused()), this.getX(), this.getY(), this.getWidth(), this.getHeight());
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        int i = this.active ? 16777215 : 10526880;
        this.renderString(guiGraphics, minecraft.font, i | Mth.ceil(this.alpha * 255.0F) << 24);
    }

    public void renderToolTip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        Minecraft minecraft = Minecraft.getInstance();
        if (this.isHoveredOrFocused()) {
            if (this.town.townComment != null) {
                guiGraphics.renderTooltip(minecraft.font, Component.nullToEmpty(this.town.townComment), mouseX, mouseY);
            }
        }
    }
}
