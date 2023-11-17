package link.botwmcs.samchai.realmshost.client.gui.components;

import link.botwmcs.samchai.realmshost.util.AnnouncementGetter;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractScrollWidget;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.LayoutSettings;
import net.minecraft.client.gui.layouts.SpacerElement;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.List;

@Environment(EnvType.CLIENT)
// TODO: Announce Text Component on PauseScreen (code from [TelemetryEventWidget])
public class AnnounceTextComponent extends AbstractScrollWidget {
    private final Content content;
    public AnnounceTextComponent(int i, int j, int k, int l) {
        super(i, j, k, l, Component.empty());
        this.content = this.buildContent();
    }

    @Override
    protected int getInnerHeight() {
        return 0;
    }

    @Override
    protected double scrollRate() {
        return 0;
    }

    @Override
    protected void renderContents(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int i = this.getY() + this.innerPadding();
        int j = this.getX() + this.innerPadding();
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate((double)j, (double)i, 0.0);
        this.content.container().visitWidgets((abstractWidget) -> {
            abstractWidget.render(guiGraphics, mouseX, mouseY, partialTick);
        });
        guiGraphics.pose().popPose();
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

    private Content buildContent() {
        ContentBuilder builder = new ContentBuilder(this.containerWidth());
        AnnouncementGetter getter = new AnnouncementGetter();
        List<String> announcement = getter.getLines();
        for (String line : announcement) {
            builder.addLine(Minecraft.getInstance().font, Component.nullToEmpty(line));
        }
        return builder.build();
    }

    private int containerWidth() {
        return this.width - this.totalInnerPadding();
    }

    @Environment(EnvType.CLIENT)
    static record Content(GridLayout container, Component narration) {
        Content(GridLayout container, Component narration) {
            this.container = container;
            this.narration = narration;
        }

        public GridLayout container() {
            return this.container;
        }

        public Component narration() {
            return this.narration;
        }
    }

    @Environment(EnvType.CLIENT)
    static class ContentBuilder {
        private final int width;
        private final GridLayout grid;
        private final GridLayout.RowHelper helper;
        private final LayoutSettings alignHeader;
        private final MutableComponent narration = Component.empty();

        public ContentBuilder(int i) {
            this.width = i;
            this.grid = new GridLayout();
            this.grid.defaultCellSetting().alignHorizontallyLeft();
            this.helper = this.grid.createRowHelper(1);
            this.helper.addChild(SpacerElement.width(i));
            this.alignHeader = this.helper.newCellSettings().alignHorizontallyCenter().paddingHorizontal(32);
        }

        public void addLine(Font font, Component message) {
            this.addLine(font, message, 0);
        }

        public void addLine(Font font, Component message, int padding) {
            this.helper.addChild((new MultiLineTextWidget(message, font)).setMaxWidth(this.width), this.helper.newCellSettings().paddingBottom(padding));
            this.narration.append(message).append("\n");
        }

        public void addHeader(Font font, Component message) {
            this.helper.addChild((new MultiLineTextWidget(message, font)).setMaxWidth(this.width - 64).setCentered(true), this.alignHeader);
            this.narration.append(message).append("\n");
        }

        public void addSpacer(int height) {
            this.helper.addChild(SpacerElement.height(height));
        }

        public AnnounceTextComponent.Content build() {
            this.grid.arrangeElements();
            return new AnnounceTextComponent.Content(this.grid, this.narration);
        }
    }
}
