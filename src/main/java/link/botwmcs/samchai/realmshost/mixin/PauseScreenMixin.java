package link.botwmcs.samchai.realmshost.mixin;

import link.botwmcs.samchai.realmshost.client.gui.components.AnnounceTextComponent;
import link.botwmcs.samchai.realmshost.client.gui.components.ColorButton;
import link.botwmcs.samchai.realmshost.config.ServerConfig;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.ShareToLanScreen;
import net.minecraft.client.gui.screens.achievement.StatsScreen;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.client.gui.screens.social.SocialInteractionsScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;

import java.util.function.Supplier;

@Mixin(PauseScreen.class)
public abstract class PauseScreenMixin extends Screen {
    @Shadow @Final private static Component RETURN_TO_GAME;

    @Shadow @Final private static Component ADVANCEMENTS;

    @Shadow @Final private static Component STATS;

    @Shadow @Final private static Component SEND_FEEDBACK;

    @Shadow @Final private static Component REPORT_BUGS;

    @Shadow @Final private static Component OPTIONS;

    @Shadow @Final private static Component SHARE_TO_LAN;

    @Shadow @Final private static Component PLAYER_REPORTING;

    @Shadow @Final private static Component RETURN_TO_MENU;

    @Shadow @Final private static Component DISCONNECT;

    @Shadow @Nullable private Button disconnectButton;

    @Shadow protected abstract Button openScreenButton(Component message, Supplier<Screen> screenSupplier);

    @Shadow protected abstract Button openLinkButton(Component message, String linkUri);

    @Shadow protected abstract void onDisconnect();

    @Shadow @Final private boolean showPauseMenu;

    @Mutable @Shadow private static Component GAME = Component.translatable("menu.game");
    static {
        if (Minecraft.getInstance().isLocalServer() || !ServerConfig.CONFIG.enableRespawnFeature.get()) {
            GAME = Component.translatable("menu.game");
        } else {
            GAME = Component.empty();
        }

    }

    private double savedScroll;

    protected PauseScreenMixin(Component component) {
        super(component);
    }

    /**
     * @author Sam_Chai
     * @reason Tweak PauseScreen
     */
    @Overwrite
    private void createPauseMenu() {
        GridLayout gridLayout = new GridLayout();
        gridLayout.defaultCellSetting().padding(4, 4, 4, 0);
        GridLayout.RowHelper rowHelper = gridLayout.createRowHelper(2);

        if (this.minecraft.isLocalServer() || !ServerConfig.CONFIG.enableLtsxFeature.get()) {
            // Vanilla part
            rowHelper.addChild(Button.builder(RETURN_TO_GAME, (button) -> {
                this.minecraft.setScreen((Screen) null);
                this.minecraft.mouseHandler.grabMouse();
            }).width(204).build(), 2, gridLayout.newCellSettings().paddingTop(50));
            rowHelper.addChild(this.openScreenButton(ADVANCEMENTS, () -> {
                return new AdvancementsScreen(this.minecraft.player.connection.getAdvancements());
            }));
            rowHelper.addChild(this.openScreenButton(STATS, () -> {
                return new StatsScreen(this, this.minecraft.player.getStats());
            }));
            rowHelper.addChild(this.openLinkButton(SEND_FEEDBACK, SharedConstants.getCurrentVersion().isStable() ? "https://aka.ms/javafeedback?ref=game" : "https://aka.ms/snapshotfeedback?ref=game"));
            ((Button)rowHelper.addChild(this.openLinkButton(REPORT_BUGS, "https://aka.ms/snapshotbugs?ref=game"))).active = !SharedConstants.getCurrentVersion().getDataVersion().isSideSeries();
            rowHelper.addChild(this.openScreenButton(OPTIONS, () -> {
                return new OptionsScreen(this, this.minecraft.options);
            }));
            if (this.minecraft.hasSingleplayerServer() && !this.minecraft.getSingleplayerServer().isPublished()) {
                rowHelper.addChild(this.openScreenButton(SHARE_TO_LAN, () -> {
                    return new ShareToLanScreen(this);
                }));
            } else {
                rowHelper.addChild(this.openScreenButton(PLAYER_REPORTING, SocialInteractionsScreen::new));
            }

            Component component = this.minecraft.isLocalServer() ? RETURN_TO_MENU : DISCONNECT;
            this.disconnectButton = (Button)rowHelper.addChild(Button.builder(component, (button) -> {
                button.active = false;
                this.minecraft.getReportingContext().draftReportHandled(this.minecraft, this, this::onDisconnect, true);
            }).width(204).build(), 2);
        } else {
            // LTSX part
            int centeredX = this.width / 2;
            int centeredY = this.height / 2;
            int buttonStartX = centeredX + 10;
            int buttonStartY = centeredY - 166 / 2;

            final AbstractWidget backToGameButton = new ColorButton(buttonStartX, buttonStartY, 100, 20, Component.translatable("gui.botwmcs.realmshost.impl.minecraft.backToGame"), 0xFF008FE1, (lambda) -> {
                this.minecraft.setScreen((Screen) null);
                this.minecraft.mouseHandler.grabMouse();
            });
            buttonStartY += 20;
            this.addRenderableWidget(backToGameButton);
            final AbstractWidget taskButton = new ColorButton(buttonStartX, buttonStartY + 2, 100, 20, Component.translatable("gui.botwmcs.realmshost.impl.minecraft.task"), 0x00FFFFFF, (lambda) -> {
                // TODO: task screen
            });
            buttonStartY += 20;
            this.addRenderableWidget(taskButton);
            final AbstractWidget playlistButton = new ColorButton(buttonStartX, buttonStartY + 4, 100, 20, Component.translatable("gui.botwmcs.realmshost.impl.minecraft.playlist"), 0x00FFFFFF, (lambda) -> {
                // TODO: playlist screen
            });
            this.addRenderableWidget(playlistButton);
            buttonStartY += 20;
            final AbstractWidget reportToBotwmcsButton = new ColorButton(buttonStartX, buttonStartY + 6, 100, 20, Component.translatable("gui.botwmcs.realmshost.impl.minecraft.reportBugToBotwmcs"), 0x00FFFFFF, (lambda) -> {
                // TODO: report screen
            });
            this.addRenderableWidget(reportToBotwmcsButton);
            buttonStartY += 20;
            final AbstractWidget optionsButton = new ColorButton(buttonStartX, buttonStartY + 8, 100, 20, Component.translatable("gui.botwmcs.realmshost.impl.minecraft.options"), 0x00FFFFFF, (lambda) -> {
                this.minecraft.setScreen(new OptionsScreen(this, this.minecraft.options));
            });
            this.addRenderableWidget(optionsButton);
            buttonStartY += 20;
            final AbstractWidget toAnotherServerButton = new ColorButton(buttonStartX, buttonStartY + 10, 100, 20, Component.translatable("gui.botwmcs.realmshost.impl.minecraft.toOtherServer"), 0xFF008FE1, (lambda) -> {
                // TODO: to anouther server screen
            });
            this.addRenderableWidget(toAnotherServerButton);
            buttonStartY += 20;
            final AbstractWidget disconnectButton = new ColorButton(buttonStartX, buttonStartY + 12, 100, 20, Component.translatable("gui.botwmcs.realmshost.impl.minecraft.exitGame"), 0xFFFF0000, (lambda) -> {
                this.minecraft.getReportingContext().draftReportHandled(this.minecraft, this, this::onDisconnect, true);
            });
            this.addRenderableWidget(disconnectButton);

            // Add announcement board
            final AnnounceTextComponent announceTextComponent = new AnnounceTextComponent(centeredX - 147 - 10, centeredY - 166 / 2, 147, 166);
            announceTextComponent.setScrollAmount(this.savedScroll);
            announceTextComponent.setOnScrolledListener((scrollAmount) -> {
                this.savedScroll = scrollAmount;
            });
            this.setInitialFocus(announceTextComponent);
            this.addRenderableWidget(announceTextComponent);

        }


        gridLayout.arrangeElements();
        FrameLayout.alignInRectangle(gridLayout, 0, 0, this.width, this.height, 0.5F, 0.25F);
        gridLayout.visitWidgets(this::addRenderableWidget);
    }

    /**
     * @author Sam_Chai
     * @reason Tweak PauseScreen
     */
    @Overwrite
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (this.minecraft.isLocalServer() || !ServerConfig.CONFIG.enableLtsxFeature.get()) {
            if (this.showPauseMenu) {
                this.renderBackground(guiGraphics);
            }
            super.render(guiGraphics, mouseX, mouseY, partialTick);
            if (this.showPauseMenu && this.minecraft != null && this.minecraft.getReportingContext().hasDraftReport() && this.disconnectButton != null) {
                guiGraphics.blit(AbstractWidget.WIDGETS_LOCATION, this.disconnectButton.getX() + this.disconnectButton.getWidth() - 17, this.disconnectButton.getY() + 3, 182, 24, 15, 15);
            }
        } else {
            if (this.showPauseMenu) {
                this.renderBackground(guiGraphics);
            }
//            this.renderAnnounceBoard(guiGraphics, mouseX, mouseY, partialTick);
            super.render(guiGraphics, mouseX, mouseY, partialTick);
        }
    }

    @Unique
    private void renderAnnounceBoard(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        ResourceLocation recipeBookGuiTextures = new ResourceLocation("textures/gui/recipe_book.png");
        int centeredX = this.width / 2;
        int centeredY = this.height / 2;
        guiGraphics.blit(recipeBookGuiTextures, centeredX - 147 - 10, centeredY - 166 / 2, 1, 1, 147, 166);

    }

}
