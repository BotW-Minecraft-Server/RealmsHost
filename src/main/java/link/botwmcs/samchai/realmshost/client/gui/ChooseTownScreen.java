package link.botwmcs.samchai.realmshost.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import link.botwmcs.samchai.realmshost.capability.town.Town;
import link.botwmcs.samchai.realmshost.client.gui.components.ColorButton;
import link.botwmcs.samchai.realmshost.network.c2s.ChooseTownC2SPacket;
import link.botwmcs.samchai.realmshost.util.TownHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChooseTownScreen extends Screen {
    private final boolean showBackground;
    private final List<Town> townList;
    private final TownButton[] townButtons = new TownButton[7];
    private static final ResourceLocation VILLAGER_TEXTURE = new ResourceLocation("textures/gui/container/villager2.png");
    private boolean isDragging;
    private int selectedTown;
    int scrollOff;

    public ChooseTownScreen(Component component, boolean showBackground) {
        super(component);
        this.showBackground = showBackground;
        this.townList = new ArrayList<>(TownHandler.getTownList(Minecraft.getInstance().level));
    }
    protected void init() {
        super.init();

        int centeredX = this.width / 2 - 108 / 2;
        int centeredY = this.height / 6;
        int buttonStartX = centeredX + 5;
        int buttonStartY = centeredY + 16 + 2;

        for(int l = 0; l < 7; ++l) {
            this.townButtons[l] = (TownButton) this.addRenderableWidget(new TownButton(buttonStartX, buttonStartY, l, (button) -> {
                if (button instanceof TownButton) {
                    this.selectedTown = ((TownButton) button).getIndex() + this.scrollOff;
                    this.postButtonClick();
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

        int centeredX = this.width / 2 - 108 / 2;
        int centeredY = this.height / 6;
        if (townList.isEmpty()) {
            guiGraphics.drawCenteredString(this.font, Component.translatable("gui.botwmcs.realmshost.chooseTownScreen.noTown"), centeredX, centeredY, 0xFFFFFF);
            final AbstractWidget randomTeleportButton = addRenderableWidget(new ColorButton(this.width / 2 - 50, this.height / 2 - 50, 100, 20, Component.translatable("gui.botwmcs.realmshost.chooseTownScreen.randomTeleport"), 0x00008FE1, (button) -> {
                // TODO: Random teleport
            }));
            this.addRenderableWidget(randomTeleportButton);
        } else {
            guiGraphics.blit(VILLAGER_TEXTURE, centeredX + 10, centeredY, 0, 180, 0.0F, 96, 165, 512, 256);
            guiGraphics.blit(VILLAGER_TEXTURE, centeredX, centeredY, 0, 0.0F, 0.0F, 101, 165, 512, 256);
        }
    }
    public void renderTransparentBackground(GuiGraphics guiGraphics) {
        guiGraphics.fillGradient(0, 0, this.width, this.height, -5, 1678774288, -2112876528);
    }
    private void renderTitles(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        Font font = Minecraft.getInstance().font;
        int centeredX = this.width / 2;
        int centeredY = this.height / 2;
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.drawCenteredString(font, Component.translatable("gui.botwmcs.realmshost.chooseTownScreen.title"), centeredX, centeredY - 100, 0xFFFFFF);
    }
    private void postButtonClick() {
        List<Town> townList1 = new ArrayList<>(this.townList);
        townList1.sort((town1, town2) -> {
            if (town1.isStared && !town2.isStared) {
                return -1;
            } else if (!town1.isStared && town2.isStared) {
                return 1;
            }
            return 0;
        });
        Town town = townList1.get(this.selectedTown);
        String townName = town.townName;
        ClientPlayNetworking.send(new ChooseTownC2SPacket(townName));
        super.onClose();
    }
    private void renderScroller(GuiGraphics guiGraphics, int posX, int posY, List<Town> townList) {
        int i = townList.size() + 1 - 7;
        if (i > 1) {
            int j = 139 - (27 + (i - 1) * 139 / i);
            int k = 1 + j / i + 139 / i;
            int m = Math.min(113, this.scrollOff * k);
            if (this.scrollOff == i - 1) {
                m = 113;
            }
            guiGraphics.blit(VILLAGER_TEXTURE, posX + 94, posY + 18 + m, 0, 0.0F, 199.0F, 6, 27, 512, 256);
        } else {
            guiGraphics.blit(VILLAGER_TEXTURE, posX + 94, posY + 18, 0, 6.0F, 199.0F, 6, 27, 512, 256);
        }
    }
    private boolean canScroll(int numOffers) {
        return numOffers > 7;
    }
    public boolean mouseScrolled(double d, double e, double f) {
        int i = this.townList.size();
        if (this.canScroll(i)) {
            int j = i - 7;
            this.scrollOff = Mth.clamp((int)((double)this.scrollOff - f), 0, j);
        }
        return true;
    }
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        int i = this.townList.size();
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
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.isDragging = false;
        int i = this.width / 2 - 108 / 2;
        int j = this.height / 6;
        if (this.canScroll(this.townList.size()) && mouseX > (double)(i + 94) && mouseX < (double)(i + 94 + 6) && mouseY > (double)(j + 18) && mouseY <= (double)(j + 18 + 139 + 1)) {
            this.isDragging = true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }


    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(guiGraphics, mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
        this.renderTitles(guiGraphics, mouseX, mouseY);
        List<Town> townList1 = new ArrayList<>(this.townList);
        townList1.sort((town1, town2) -> {
            if (town1.isStared && !town2.isStared) {
                return -1;
            } else if (!town1.isStared && town2.isStared) {
                return 1;
            }
            return 0;
        });
        if (!townList1.isEmpty()) {
            int bgGuiStartX = this.width / 2 - 108 / 2; // in vanilla, it called int i
            int bgGuiStartY = this.height / 6; // int j
            int k = bgGuiStartY + 16 + 6;
            int l = bgGuiStartY + 5 + 5;
            this.renderScroller(guiGraphics, bgGuiStartX, bgGuiStartY, this.townList);
            int m = 0;
            Iterator<Town> iterator = townList1.iterator();

            while (true) {
                Town town;
                while (iterator.hasNext()) {
                    town = iterator.next();
                    if (this.canScroll(townList1.size()) && (m < this.scrollOff || m >= 7 + this.scrollOff)) {
                        ++m;
                    } else {
                        String townName = town.townName;
                        ItemStack itemStack = new ItemStack(Items.NETHER_STAR, 1);
                        guiGraphics.pose().pushPose();
                        guiGraphics.pose().translate(0.0F, 0.0F, 100.0F);
                        int n = k + 2;
                        guiGraphics.drawCenteredString(this.font, Component.nullToEmpty(townName), this.width / 2 - 5, n, 0xFFFFFF);
                        if (town.isStared) {
                            guiGraphics.renderFakeItem(itemStack, this.width / 2 - 45, n - 5);
                        }
                        guiGraphics.pose().popPose();
                        k += 20;
                        ++m;
                    }
                }
                TownButton[] buttons = this.townButtons;
                int buttonsLength = buttons.length;
                for (int o = 0; o < buttonsLength; ++o) {
                    TownButton button = buttons[o];
                    if (button.isHoveredOrFocused()) {
//                        button.renderToolTip(guiGraphics, mouseX, mouseY);
                    }
                    button.visible = button.index < this.townList.size();
                }
                RenderSystem.enableDepthTest();
                break;
            }
        }

    }


    @Environment(EnvType.CLIENT)
    private class TownButton extends ColorButton {
        final int index;

        public TownButton(int x, int y, int index, OnPress onPress) {
            super(x, y, 88, 20, CommonComponents.EMPTY, 0x00FFFFFF, onPress);
            this.index = index;
            this.visible = false;
        }
        public int getIndex() {
            return this.index;
        }
        public void renderToolTip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
            if (this.isHovered && ChooseTownScreen.this.townList.size() > this.index + ChooseTownScreen.this.scrollOff) {
                String townComment = ChooseTownScreen.this.townList.get(this.index).townComment;
                guiGraphics.renderTooltip(ChooseTownScreen.this.font, Component.nullToEmpty(townComment), mouseX, mouseY);
            }
        }
    }
}
