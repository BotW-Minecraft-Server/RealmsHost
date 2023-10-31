package link.botwmcs.samchai.realmshost.mixin.impl.rei;

import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.rei.api.client.REIRuntime;
import me.shedaniel.rei.api.client.config.addon.ConfigAddonRegistry;
import me.shedaniel.rei.api.client.overlay.ScreenOverlay;
import me.shedaniel.rei.api.client.registry.entry.EntryRegistry;
import me.shedaniel.rei.impl.client.REIRuntimeImpl;
import me.shedaniel.rei.impl.client.config.ConfigManagerImpl;
import me.shedaniel.rei.impl.client.config.addon.ConfigAddonRegistryImpl;
import me.shedaniel.rei.impl.client.config.entries.ConfigAddonsEntry;
import me.shedaniel.rei.impl.client.config.entries.ReloadPluginsEntry;
import me.shedaniel.rei.impl.client.gui.ScreenOverlayImpl;
import me.shedaniel.rei.impl.client.gui.performance.entry.PerformanceEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Mixin(value = ConfigManagerImpl.class, remap = false)
public abstract class ConfigManagerImplMixin {
    @Shadow public abstract void saveConfig();
    @ModifyArg(method = "getConfigScreen", at = @At(value = "INVOKE", target = "Lme/shedaniel/autoconfig/gui/ConfigScreenProvider;setBuildFunction(Ljava/util/function/Function;)V"))
    private Function<ConfigBuilder, Screen> modifyBuildFunction(Function<ConfigBuilder, Screen> original) {
        class EmptyEntry extends AbstractConfigListEntry<Object> {
            private final int height;

            public EmptyEntry(int height) {
                super(Component.literal(UUID.randomUUID().toString()), false);
                this.height = height;
            }

            public int getItemHeight() {
                return this.height;
            }

            public Object getValue() {
                return null;
            }

            public Optional<Object> getDefaultValue() {
                return Optional.empty();
            }

            public boolean isMouseInside(int mouseX, int mouseY, int x, int y, int entryWidth, int entryHeight) {
                return false;
            }

            public void save() {
            }

            public void render(GuiGraphics graphics, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean isHovered, float delta) {
            }

            public List<? extends GuiEventListener> children() {
                return Collections.emptyList();
            }

            public List<? extends NarratableEntry> narratables() {
                return Collections.emptyList();
            }
        }

        return (ConfigBuilder builder) -> {
            if (Minecraft.getInstance().getConnection() != null && Minecraft.getInstance().getConnection().getRecipeManager() != null) {
                builder.getOrCreateCategory(Component.translatable("config.roughlyenoughitems.advanced")).getEntries().add(0, new ReloadPluginsEntry(220));
                builder.getOrCreateCategory(Component.translatable("config.roughlyenoughitems.advanced")).getEntries().add(0, new PerformanceEntry(220));
            }
            ConfigAddonRegistryImpl addonRegistry = (ConfigAddonRegistryImpl) ConfigAddonRegistry.getInstance();
            if (!addonRegistry.getAddons().isEmpty()) {
                builder.getOrCreateCategory(Component.translatable("config.roughlyenoughitems.basics")).getEntries().add(0, new EmptyEntry(4));
                builder.getOrCreateCategory(Component.translatable("config.roughlyenoughitems.basics")).getEntries().add(0, new ConfigAddonsEntry(220));
            }
            builder.getOrCreateCategory(Component.translatable("config.roughlyenoughitems.basics")).getEntries().add(0, new EmptyEntry(4));
            builder.getOrCreateCategory(Component.translatable("config.roughlyenoughitems.basics")).getEntries().add(0, new EmptyEntry(4));

            return builder.setAfterInitConsumer(screen -> {
            }).setSavingRunnable(() -> {
                this.saveConfig();
                EntryRegistry.getInstance().refilter();
                REIRuntime.getInstance().getOverlay().ifPresent(ScreenOverlay::queueReloadOverlay);
                if (REIRuntimeImpl.getSearchField() != null) {
                    ScreenOverlayImpl.getEntryListWidget().updateSearch(REIRuntimeImpl.getSearchField().getText(), true);
                }
            }).build();
        };
    }

}
