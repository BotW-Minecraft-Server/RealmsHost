package link.botwmcs.samchai.realmshost.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import link.botwmcs.samchai.realmshost.capability.DeathCounter;
import link.botwmcs.samchai.realmshost.capability.Home;
import link.botwmcs.samchai.realmshost.client.gui.components.ColorButton;
import link.botwmcs.samchai.realmshost.network.c2s.RespawnPlayerOnPlaceC2SPacket;
import link.botwmcs.samchai.realmshost.util.CapabilitiesHandler;
import link.botwmcs.samchai.realmshost.util.ServerUtilities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.*;

public class ChooseRespawnScreen extends Screen {
    private static final ResourceLocation VILLAGER_TEXTURE = new ResourceLocation("textures/gui/container/villager2.png");
    private final boolean showBackground;
    private final Player player;
    private final List<Home> homeList;
    private final List<DeathCounter> deathCounterList;
    private final RespawnButton[] respawnButtons = new RespawnButton[7];
    private boolean isDragging;
    private int selectedRespawnPlace;
    int scrollOff;
    public ChooseRespawnScreen(Component component, Player player, boolean showBackground) {
        super(component);
        this.player = player;
        this.showBackground = showBackground;
        this.homeList = new ArrayList<>(CapabilitiesHandler.getPlayerHomeList(player));
        this.deathCounterList = new ArrayList<>(CapabilitiesHandler.getPlayerDeathCounterList(player));
    }
    protected void init() {
        super.init();
        int centeredX = this.width / 2 - 108 / 2;
        int centeredY = this.height / 6;
        int xOffset = centeredX + 100;
        int buttonStartX = xOffset + 5;
        int buttonStartY = centeredY + 16 + 2;

        for (int l = 0; l < 7; ++l) {
            this.respawnButtons[l] = this.addRenderableWidget(new RespawnButton(buttonStartX, buttonStartY, l, 0xFFFFFF, (button) -> {
                if (button instanceof RespawnButton) {
                    this.selectedRespawnPlace = ((RespawnButton) button).getIndex() + this.scrollOff;
                    this.postButtonClick(this.selectedRespawnPlace);
                }
            }));
            buttonStartY += 20;
        }
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
        guiGraphics.blit(VILLAGER_TEXTURE, xOffset + 10, centeredY, 0, 180, 0.0F, 96, 165, 512, 256);
        guiGraphics.blit(VILLAGER_TEXTURE, xOffset, centeredY, 0, 0.0F, 0.0F, 101, 165, 512, 256);
    }
    public void renderTransparentBackground(GuiGraphics guiGraphics) {
        guiGraphics.fillGradient(0, 0, this.width, this.height, -5, 1678774288, -2112876528);
    }
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(guiGraphics, mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
        List<Home> homeList1 = new ArrayList<>(this.homeList);
        List<DeathCounter> deathCounterList1 = new ArrayList<>(this.deathCounterList);
        // Sorting
        homeList1.sort(Comparator.comparing(home -> home.homeName));
        deathCounterList1.sort((dc1, dc2) -> Long.compare(dc2.deathTime, dc1.deathTime));
        Collections.reverse(deathCounterList1);

        int combinedListSize = homeList1.size() + deathCounterList1.size();
        List<Object> combinedList = new ArrayList<>();
        combinedList.addAll(homeList1);
        combinedList.addAll(deathCounterList1);
        if (!combinedList.isEmpty()) {
            int bgGuiStartX = this.width / 2 - 108 / 2 + 100; // in vanilla, it called int i
            int bgGuiStartY = this.height / 6; // int j
            int k = bgGuiStartY + 16 + 6;
            int l = bgGuiStartY + 5 + 5;
            // TODO: Tweak render X and Y
            int bgGuiCentralX = bgGuiStartX + 108 / 2 - 5;
            int m = 0;
            this.renderScroller(guiGraphics, bgGuiStartX, bgGuiStartY, homeList1, deathCounterList1);
            for (Object item : combinedList) {
                if (this.canScroll(combinedListSize) && (m < this.scrollOff || m >= 7 + this.scrollOff)) {
                    m++;
                } else {
                    Component component = null;
                    if (item instanceof Home) {
                        Home home = (Home) item;
                        component = Component.nullToEmpty(home.homeName);
                        ItemStack homeItemIcon = new ItemStack(Items.COMPASS, 1);
                        guiGraphics.pose().pushPose();
                        guiGraphics.pose().translate(0.0F, 0.0F, 100.0F);
                        int n = k + 2;
                        guiGraphics.drawCenteredString(this.font, component, bgGuiCentralX, n, 0xFFFFFF);
                        guiGraphics.renderFakeItem(homeItemIcon, bgGuiCentralX - 40, n - 4);
                        guiGraphics.pose().popPose();
                        k += 20;
                        m++;
                    } else if (item instanceof DeathCounter) {
                        component = Component.translatable("gui.botwmcs.realmshost.respawnScreen.deathPlace");
                        ItemStack deathCounterItemIcon = new ItemStack(Items.BONE, 1);
                        guiGraphics.pose().pushPose();
                        guiGraphics.pose().translate(0.0F, 0.0F, 100.0F);
                        int n = k + 2;
                        guiGraphics.drawCenteredString(this.font, component, bgGuiCentralX, n, 0xFFFFFF);
                        guiGraphics.renderFakeItem(deathCounterItemIcon, bgGuiCentralX - 40, n - 4);
                        guiGraphics.pose().popPose();
                        k += 20;
                        m++;
                    }
//                    ++m;
                }
            }
            RespawnButton[] buttons = this.respawnButtons;
            int buttonsLength = buttons.length;
            for (int o = 0; o < buttonsLength; ++o) {
                RespawnButton respawnButton = buttons[o];
                if (respawnButton.isHoveredOrFocused()) {
                    respawnButton.renderToolTip(guiGraphics, mouseX, mouseY, respawnButton.index + this.scrollOff);
                }
                respawnButton.visible = respawnButton.index < this.homeList.size() + this.deathCounterList.size();
            }
            RenderSystem.enableDepthTest();
        }
    }

    private void renderScroller(GuiGraphics guiGraphics, int posX, int posY, List<Home> homeList, List<DeathCounter> deathCounterList) {
        int i = homeList.size() + deathCounterList.size() + 1 - 7;
        if (i > 1) {
            int j = 139 - (27 + (i - 1) * 139 / i);
            int k = 1 + j / i + 139 / i;
            int m = Math.min(113, this.scrollOff * k);
            if (this.scrollOff == i - 1) {
                m = 113;
            }
            int SCROLLER_HEIGHT = 27;
            int SCROLLER_WIDTH = 6;
            guiGraphics.blit(VILLAGER_TEXTURE, posX + 94, posY + 18 + m, 0, 0.0F, 199.0F, 6, 27, 512, 256);
        } else {
            guiGraphics.blit(VILLAGER_TEXTURE, posX + 94, posY + 18, 0, 6.0F, 199.0F, 6, 27, 512, 256);
        }
    }
    private boolean canScroll(int numOffers) {
        return numOffers > 7;
    }
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        int i = this.homeList.size() + this.deathCounterList.size();
        if (this.canScroll(i)) {
            int j = i - 7;
            this.scrollOff = Mth.clamp((int)((double)this.scrollOff - delta), 0, j);
        }
        return true;
    }
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        int i = this.homeList.size() + this.deathCounterList.size();
        if (this.isDragging) {
            int j = this.height / 6 + 18;
            int k = j + 139;
            int l = i - 7;
            float f = ((float)mouseY - (float)j - 13.5F) / ((float)(k - j) - 27.0F);
            f = f * (float)l + 0.5F;
            this.scrollOff = Mth.clamp((int)f, 0, l);
            return true;
        } else {
            return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
        }
    }
    public boolean mouseClicked(double mouseX, double mouseY, int buttonIndex) {
        this.isDragging = false;
        int i = this.width / 2 - 108 / 2 + 100;
        int j = this.height / 6;
        if (this.canScroll(this.homeList.size() + this.deathCounterList.size()) && mouseX > (double)(i + 94) && mouseX < (double)(i + 94 + 6) && mouseY > (double)(j + 18) && mouseY <= (double)(j + 18 + 139 + 1)) {
            this.isDragging = true;
        }

        return super.mouseClicked(mouseX, mouseY, buttonIndex);

    }

    @Environment(EnvType.CLIENT)
    private class RespawnButton extends ColorButton {
        final int index;
        final int color;
        public RespawnButton(int x, int y, int index, int color, OnPress onPress) {
            super(x, y, 88, 20, CommonComponents.EMPTY, color, onPress);
            this.index = index;
            this.color = color;
            this.visible = false;
        }
        public int getIndex() {
            return this.index;
        }
        public void renderToolTip(GuiGraphics guiGraphics, int mouseX, int mouseY, int index) {
            if (this.isHovered) {
                List<Component> componentList = new ArrayList<>();
                if (index < homeList.size()) {
                    Home home = homeList.get(index);
                    componentList.add(Component.translatable("gui.botwmcs.realmshost.respawnScreen.home.homeName", home.homeName));
                    componentList.add(Component.translatable("gui.botwmcs.realmshost.respawnScreen.home.homeLevel", home.homeLevel.location()));
                    componentList.add(Component.translatable("gui.botwmcs.realmshost.respawnScreen.home.homePos", home.homePos));
                } else {
                    int deathIndex = index - homeList.size();
                    if (deathIndex < deathCounterList.size()) {
                        DeathCounter deathCounter = deathCounterList.get(deathIndex);
                        componentList.add(Component.translatable("gui.botwmcs.realmshost.respawnScreen.deathPlace.deathTime", ServerUtilities.convertTimestampToReadableTime(deathCounter.deathTime, "HH:mm:ss")));
                        componentList.add(Component.translatable("gui.botwmcs.realmshost.respawnScreen.deathPlace.deathLevel", deathCounter.deathLevel.location()));
                        componentList.add(Component.translatable("gui.botwmcs.realmshost.respawnScreen.deathPlace.deathPos", deathCounter.deathPos));
                        guiGraphics.renderTooltip(font, componentList, Optional.empty(), mouseX, mouseY);
                    }
                }
                guiGraphics.renderTooltip(font, componentList, Optional.empty(), mouseX, mouseY);
            }
        }
    }
    private void postButtonClick(int index) {
        if (index < homeList.size()) {
            Home home = homeList.get(index);
            player.respawn();
            ClientPlayNetworking.send(new RespawnPlayerOnPlaceC2SPacket(home.homeLevel, home.homePos));
            super.onClose();
        } else {
            int deathIndex = index - homeList.size();
            DeathCounter deathCounter = deathCounterList.get(deathIndex);
            player.respawn();
            ClientPlayNetworking.send(new RespawnPlayerOnPlaceC2SPacket(deathCounter.deathLevel, deathCounter.deathPos));
            super.onClose();
        }
    }
}
