package link.botwmcs.samchai.realmshost.mixin;

import link.botwmcs.samchai.realmshost.client.gui.ChooseRespawnScreen;
import link.botwmcs.samchai.realmshost.client.gui.components.ColorButton;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(value = DeathScreen.class)
public abstract class DeathScreenMixin extends Screen {

    @Shadow private int delayTicker;

    @Shadow @Final private List<Button> exitButtons;

    @Shadow @Final private boolean hardcore;

    @Shadow @Nullable private Button exitToTitleButton;

    @Shadow protected abstract void setButtonsActive(boolean active);

    @Shadow private Component deathScore;

    @Shadow protected abstract void handleExitToTitleScreen();

    @Shadow public abstract boolean mouseClicked(double mouseX, double mouseY, int button);

    protected DeathScreenMixin(Component component) {
        super(component);
    }

    /**
     * @author Sam_Chai
     * @reason Rewrite DeathScreen, add some LTSX support components
     */
    @Overwrite
    public void init() {
        if (this.minecraft.isLocalServer()) {
            this.delayTicker = 0;
            this.exitButtons.clear();
            Component component = this.hardcore ? Component.translatable("deathScreen.spectate") : Component.translatable("deathScreen.respawn");
            this.exitButtons.add((Button)this.addRenderableWidget(Button.builder(component, (button) -> {
                this.minecraft.player.respawn();
                button.active = false;
            }).bounds(this.width / 2 - 100, this.height / 4 + 72, 200, 20).build()));
            this.exitToTitleButton = (Button)this.addRenderableWidget(Button.builder(Component.translatable("deathScreen.titleScreen"), (button) -> {
                this.minecraft.getReportingContext().draftReportHandled(this.minecraft, this, this::handleExitToTitleScreen, true);
            }).bounds(this.width / 2 - 100, this.height / 4 + 96, 200, 20).build());
            this.exitButtons.add(this.exitToTitleButton);
            this.setButtonsActive(false);
            this.deathScore = Component.translatable("deathScreen.score.value", new Object[]{Component.literal(Integer.toString(this.minecraft.player.getScore())).withStyle(ChatFormatting.YELLOW)});

        } else {
            this.delayTicker = 0;
            this.exitButtons.clear();
            final AbstractWidget respawnNearByButton = addRenderableWidget(new ColorButton(this.width / 2 - 100, this.height / 4 + 72, 200, 20, Component.translatable("gui.botwmcs.realmshost.deathScreen.respawnNearBy"), 0x00FFFFFF, sender -> {
                this.minecraft.player.respawn();
                sender.active = false;
            }));
            final AbstractWidget respawnChooseButton = addRenderableWidget(new ColorButton(this.width / 2 - 100, this.height / 4 + 96, 200, 20, Component.translatable("gui.botwmcs.realmshost.deathScreen.respawnChoose"), 0x00FFFFFF, sender -> {
                minecraft.setScreen(new ChooseRespawnScreen(Component.translatable("gui.botwmcs.realmshost.deathScreen.respawnChoose"), this.minecraft.player, false));
                sender.active = false;
            }));
            this.exitButtons.add((Button)respawnNearByButton);
            this.exitButtons.add((Button)respawnChooseButton);
            this.setButtonsActive(false);
            this.deathScore = Component.translatable("gui.botwmcs.realmshost.deathScreen.deathPos", this.minecraft.player.getBlockX(), this.minecraft.player.getBlockY(), this.minecraft.player.getBlockZ());
        }
    }


}
