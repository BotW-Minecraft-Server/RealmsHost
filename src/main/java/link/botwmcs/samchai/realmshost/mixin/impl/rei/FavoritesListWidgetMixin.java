package link.botwmcs.samchai.realmshost.mixin.impl.rei;

import me.shedaniel.rei.impl.client.gui.widget.favorites.FavoritesListWidget;
import me.shedaniel.rei.impl.client.gui.widget.favorites.panel.FavoritesPanel;
import me.shedaniel.rei.impl.client.gui.widget.favorites.panel.FavoritesTogglePanelButton;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = FavoritesListWidget.class, remap = false)
public class FavoritesListWidgetMixin {
    @Shadow @Final public FavoritesPanel favoritePanel;

    @Shadow @Final public FavoritesTogglePanelButton togglePanelButton;

    /**
     * @author Sam_Chai
     * @reason Disable panel button but keep favorite list
     */
    @Overwrite
    private void renderAddFavorite(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        this.favoritePanel.render(graphics, mouseX, mouseY, delta);
    }
}
