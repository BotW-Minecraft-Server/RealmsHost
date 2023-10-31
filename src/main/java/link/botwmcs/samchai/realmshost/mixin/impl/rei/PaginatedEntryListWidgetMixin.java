package link.botwmcs.samchai.realmshost.mixin.impl.rei;

import link.botwmcs.samchai.realmshost.client.gui.components.ColorButton;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.config.ConfigObject;
import me.shedaniel.rei.api.client.gui.config.SearchFieldLocation;
import me.shedaniel.rei.api.client.gui.widgets.Button;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.impl.client.gui.ScreenOverlayImpl;
import me.shedaniel.rei.impl.client.gui.changelog.ChangelogLoader;
import me.shedaniel.rei.impl.client.gui.widget.entrylist.CollapsingEntryListWidget;
import me.shedaniel.rei.impl.client.gui.widget.entrylist.PaginatedEntryListWidget;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;


@Mixin(value = PaginatedEntryListWidget.class, remap = false)
public abstract class PaginatedEntryListWidgetMixin extends CollapsingEntryListWidget {
    @Shadow private Button changelogButton;

    @Shadow private List<Widget> additionalWidgets;

    @Inject(method = "init", at = @At(value = "TAIL"), remap = false)
    protected void modifyChangelogButton(ScreenOverlayImpl overlay, CallbackInfo callbackInfo) {
        this.additionalWidgets.remove(changelogButton);
        this.additionalWidgets.remove(2); // Remove changelog button icon
        Rectangle overlayBounds = overlay.getBounds();
        Button createButton = ((Button) Widgets.createButton(new Rectangle(overlayBounds.getMaxX() - 18 - 18, overlayBounds.y + (ConfigObject.getInstance().getSearchFieldLocation() == SearchFieldLocation.TOP_SIDE ? 24 : 0) + 5, 16, 16), Component.nullToEmpty("C")).onClick((button) -> {
//            ChangelogLoader.show();
        }).containsMousePredicate((button, point) -> {
            return button.getBounds().contains(point) && overlay.isNotInExclusionZones((double)point.x, (double)point.y);
        })).tooltipLine(Component.translatable("gui.botwmcs.realmshost.impl.jei.openCreatePonder")).focusable(false);
        this.additionalWidgets.add(createButton);

    }
}
