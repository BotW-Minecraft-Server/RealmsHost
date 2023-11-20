package link.botwmcs.samchai.realmshost.client.gui.components;

import com.mojang.blaze3d.vertex.PoseStack;
import link.botwmcs.samchai.realmshost.RealmsHost;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.GameType;

public class PlayerHUD {
    private static Component currentComponent;
    private static int tick = 0;
    private static LerpedFloatCalculator componentSize = LerpedFloatCalculator.linear();
    private static PlayerHUD instance = new PlayerHUD();

    public void onShowHUDMessage(Component component, int hiddenTick) {
        currentComponent = component;
        tick = hiddenTick;
    }

    public void tick() {
        if (tick > 0) {
            tick--;
        } else {
            currentComponent = null;
        }
        componentSize.chase(currentComponent != null ? Minecraft.getInstance().font.width(currentComponent) + 17 : 0.0F, 0.1F, LerpedFloatCalculator.Chaser.EXP);
        componentSize.tickChaser();
    }

    public void render(GuiGraphics guiGraphics, float tickDelta) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.options.hideGui || minecraft.gameMode.getPlayerMode() == GameType.SPECTATOR) {
            return;
        }
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.translate(minecraft.getWindow().getGuiScaledWidth() / 2 - 91, minecraft.getWindow().getGuiScaledHeight() - 29, 0);
        int size = (int) componentSize.getValue(tickDelta);
        if (size > 1) {
            poseStack.pushPose();
            poseStack.translate(size / -2F + 91, -27, 100);
            // left blank
            ResourceLocation createWidgets = new ResourceLocation(RealmsHost.MODID, "textures/gui/create_widgets.png");
            guiGraphics.blit(createWidgets, -3, 0, 8, 209, 3, 16, 256, 256);
            // right blank
            guiGraphics.blit(createWidgets, size, 0, 11, 209, 3, 16, 256, 256);
            // component bg
            guiGraphics.blit(createWidgets, 0, 0, 0, 0 + (128 - size / 2F), 230, size, 16, 256, 256);
            poseStack.popPose();

            // render text
            Font font = minecraft.font;
            if (currentComponent != null && font.width(currentComponent) < size - 10) {
                poseStack.pushPose();
                poseStack.translate(font.width(currentComponent) / 2F + 82, -27, 100);
                guiGraphics.drawCenteredString(font, currentComponent, 9 - font.width(currentComponent) / 2, 4, 0xFFFFFF);
                poseStack.popPose();
            }
        }
        poseStack.translate(91, -9, 0);
        poseStack.scale(0.925f, 0.925f, 1);
        poseStack.popPose();


    }

    public static PlayerHUD getInstance() {
        return instance;
    }
}
