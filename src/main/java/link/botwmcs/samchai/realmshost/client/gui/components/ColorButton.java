package link.botwmcs.samchai.realmshost.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class ColorButton extends Button {
    int color;
    private static final ResourceLocation WIDGETS_TEXTURE = new ResourceLocation("textures/gui/widgets.png");

    public ColorButton(int x, int y, int buttonWidth, int buttonHeight, Component component, int color, OnPress onPress) {
        super(x, y, buttonWidth, buttonHeight, component, onPress, Supplier::get);
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
        guiGraphics.blitNineSliced(WIDGETS_TEXTURE, this.getX(), this.getY(), this.getWidth(), this.getHeight(), 20, 4, 200, 20, 0, this.getTextureY());
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        int i = this.active ? 16777215 : 10526880;
        this.renderString(guiGraphics, minecraft.font, i | Mth.ceil(this.alpha * 255.0F) << 24);
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
