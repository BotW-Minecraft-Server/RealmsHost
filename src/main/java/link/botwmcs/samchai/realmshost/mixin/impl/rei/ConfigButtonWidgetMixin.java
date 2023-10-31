package link.botwmcs.samchai.realmshost.mixin.impl.rei;

import me.shedaniel.rei.api.client.ClientHelper;
import me.shedaniel.rei.api.client.REIRuntime;
import me.shedaniel.rei.api.client.config.ConfigManager;
import me.shedaniel.rei.api.client.favorites.FavoriteMenuEntry;
import me.shedaniel.rei.api.client.gui.config.DisplayPanelLocation;
import me.shedaniel.rei.api.client.gui.config.SyntaxHighlightingMode;
import me.shedaniel.rei.impl.client.ClientHelperImpl;
import me.shedaniel.rei.impl.client.config.ConfigManagerImpl;
import me.shedaniel.rei.impl.client.config.ConfigObjectImpl;
import me.shedaniel.rei.impl.client.gui.modules.entries.*;
import me.shedaniel.rei.impl.client.gui.widget.ConfigButtonWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.BooleanSupplier;

@Mixin(value = ConfigButtonWidget.class, remap = false)
public class ConfigButtonWidgetMixin {
    /**
     * @author Sam_Chai
     * @reason Remove cheat toggle
     */
    @Overwrite
    private static Collection<FavoriteMenuEntry> menuEntries() {
        ConfigObjectImpl config = ConfigManagerImpl.getInstance().getConfig();
        MutableComponent var10004 = Component.translatable("text.rei.config.menu.dark_theme");
        Objects.requireNonNull(config);
        ToggleMenuEntry var9 = ToggleMenuEntry.ofDeciding(var10004, config::isUsingDarkTheme, (dark) -> {
            config.setUsingDarkTheme(dark);
            return false;
        });
        MutableComponent var10005 = Component.translatable("text.rei.config.menu.craftable_filter");
        Objects.requireNonNull(config);
        BooleanSupplier var10006 = config::isCraftableFilterEnabled;
        Objects.requireNonNull(config);
        ToggleMenuEntry var1 = ToggleMenuEntry.of(var10005, var10006, config::setCraftableFilterEnabled);
        MutableComponent var10008 = Component.translatable("text.rei.config.menu.display");
        MutableComponent var10009 = Component.translatable("text.rei.config.menu.display.remove_recipe_book");
        Objects.requireNonNull(config);
        ToggleMenuEntry var2 = ToggleMenuEntry.of(var10009, config::doesDisableRecipeBook, (disableRecipeBook) -> {
            config.setDisableRecipeBook(disableRecipeBook);
            Screen screen = Minecraft.getInstance().screen;
            if (screen != null) {
                screen.init(Minecraft.getInstance(), screen.width, screen.height);
            }

        });
        MutableComponent var10010 = Component.translatable("text.rei.config.menu.display.left_side_mob_effects");
        Objects.requireNonNull(config);
        ToggleMenuEntry var3 = ToggleMenuEntry.of(var10010, config::isLeftSideMobEffects, (disableRecipeBook) -> {
            config.setLeftSideMobEffects(disableRecipeBook);
            Screen screen = Minecraft.getInstance().screen;
            if (screen != null) {
                screen.init(Minecraft.getInstance(), screen.width, screen.height);
            }

        });
        MutableComponent var10011 = Component.translatable("text.rei.config.menu.display.left_side_panel");
        Objects.requireNonNull(config);
        ToggleMenuEntry var4 = ToggleMenuEntry.of(var10011, config::isLeftHandSidePanel, (bool) -> {
            config.setDisplayPanelLocation(bool ? DisplayPanelLocation.LEFT : DisplayPanelLocation.RIGHT);
        });
        MutableComponent var10012 = Component.translatable("text.rei.config.menu.display.scrolling_side_panel");
        Objects.requireNonNull(config);
        BooleanSupplier var10013 = config::isEntryListWidgetScrolled;
        Objects.requireNonNull(config);
        ToggleMenuEntry var5 = ToggleMenuEntry.of(var10012, var10013, config::setEntryListWidgetScrolled);
        SeparatorMenuEntry var6 = new SeparatorMenuEntry();
        MutableComponent var10014 = Component.translatable("text.rei.config.menu.display.caching_entry_rendering");
        Objects.requireNonNull(config);
        BooleanSupplier var10015 = config::doesCacheEntryRendering;
        Objects.requireNonNull(config);
        return List.of(var9, var1, new SubMenuEntry(var10008, List.of(var2, var3, var4, var5, var6, ToggleMenuEntry.of(var10014, var10015, config::setDoesCacheEntryRendering), new SeparatorMenuEntry(), ToggleMenuEntry.of(Component.translatable("text.rei.config.menu.display.syntax_highlighting"), () -> {
            return config.getSyntaxHighlightingMode() == SyntaxHighlightingMode.COLORFUL || config.getSyntaxHighlightingMode() == SyntaxHighlightingMode.COLORFUL_UNDERSCORED;
        }, (bool) -> {
            config.setSyntaxHighlightingMode(bool ? SyntaxHighlightingMode.COLORFUL : SyntaxHighlightingMode.PLAIN_UNDERSCORED);
        }))), new SeparatorMenuEntry(), ToggleMenuEntry.ofDeciding(Component.translatable("text.rei.config.menu.config"), () -> {
            return false;
        }, ($) -> {
            ConfigManager.getInstance().openConfigScreen(REIRuntime.getInstance().getPreviousScreen());
            return false;
        }));
    }

}
