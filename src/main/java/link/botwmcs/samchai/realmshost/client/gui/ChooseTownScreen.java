package link.botwmcs.samchai.realmshost.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import link.botwmcs.samchai.realmshost.RealmsHost;
import link.botwmcs.samchai.realmshost.capability.town.Town;
import link.botwmcs.samchai.realmshost.capability.town.TownCompoundHandler;
import link.botwmcs.samchai.realmshost.network.c2s.ChooseTownC2SPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MerchantMenu;

import java.util.*;


public class ChooseTownScreen extends Screen {
    private final boolean showBackground;
    private final Player localPlayer;
    private final ResourceLocation recipeBookGuiTextures = new ResourceLocation("textures/gui/recipe_book.png");
    private final ResourceLocation SCROLLER_SPRITE = new ResourceLocation("container/villager/scroller");
    private final ResourceLocation SCROLLER_DISABLED_SPRITE = new ResourceLocation("container/villager/scroller_disabled");
    private final TownButton[] townButtons = new TownButton[7];
    private int townCount = 0;
    private Map<String, Town> townSet;
    private boolean isDragging;
    int scrollOff;

    public ChooseTownScreen(Component component, Player player, boolean showBackground) {
        super(component);
        this.showBackground = showBackground;
        this.localPlayer = player;
        Map<String, Town> townMap = Minecraft.getInstance().level.getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).getAllTowns();
        this.townSet = townMap;
        for (Map.Entry<String, Town> entry : townMap.entrySet()) {
            this.townCount++;
        }
    }

    @Override
    protected void init() {
        super.init();
//        this.renderButtons();
        int buttonStartX = this.width / 2 - 147 / 2 + 8;
        int buttonStartY = this.height / 2 - 166 / 2 + 8;
        int buttonPlusStartY = buttonStartY;
        // Because of using AutoSyncComponent from CCA, the client side will sync from server's
        Map<String, Town> townMap = Minecraft.getInstance().level.getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).getAllTowns();
        List<Map.Entry<String, Town>> townList = new ArrayList<>(townMap.entrySet());
        for(int l = 0; l < 7; ++l) {
            Map.Entry<String, Town> entry = townList.get(l + this.scrollOff);
            Town thisTown = entry.getValue();
            this.townButtons[l] = this.addRenderableWidget(new TownButton(buttonStartX, buttonPlusStartY, 147 - 16, 20, thisTown, l, Component.nullToEmpty(thisTown.townName), (button) -> {
                if (button instanceof TownButton) {
                    TownButton townButton = (TownButton) button;
                }
            }));
            buttonPlusStartY += 20;
        }

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
        this.renderScrollableList(guiGraphics, mouseX, mouseY, delta);
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
    private void renderScrollableList(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        if (this.townCount != 0) {
            this.renderScrollBar(guiGraphics);
            while (true) {
                TownButton[] buttons = this.townButtons;
                for (int j = 0; j < buttons.length; ++j) {
                    TownButton townButton = buttons[j];
                    if (townButton.isHoveredOrFocused()) {
                        townButton.renderToolTip(guiGraphics, mouseX, mouseY);
                    }
                    townButton.visible = townButton.index < this.townCount;
                }
                RenderSystem.enableDepthTest();
                break;
            }
        }
    }
    private void renderButtons() {

    }
    private void renderScrollBar(GuiGraphics guiGraphics) {
        int townCount = this.townCount;
        int i = townCount + 1 - 7;
        if (i > 1) {
            int j = 139 - (27 + (i - 1) * 139 / i);
            int k = 1 + j / i + 139 / i;
            int m = Math.min(113, this.scrollOff * k);
            if (this.scrollOff == i - 1) {
                m = 113;
            }
            int buttonStartX = this.width / 2 - 147 / 2 + 8;
            int buttonStartY = this.height / 2 - 166 / 2 + 8;
            guiGraphics.blitSprite(SCROLLER_SPRITE, buttonStartX + 147 - 8, buttonStartY + m, 0, 6, 27);
        }
    }
    private boolean canScroll(int numOffers) {
        return numOffers > 7;
    }
    public boolean mouseScrolled(double d, double e, double f, double g) {
        if (this.canScroll(this.townCount)) {
            int j = this.townCount - 7;
            this.scrollOff = Mth.clamp((int)((double)this.scrollOff - g), 0, j);
        }
        RealmsHost.LOGGER.info(String.valueOf(this.scrollOff));
        return true;
    }
    private void setPlayerTown(String townName) {
        ClientPlayNetworking.send(new ChooseTownC2SPacket(townName));
        super.onClose();
    }

}
