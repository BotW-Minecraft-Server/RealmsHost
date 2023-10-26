package link.botwmcs.samchai.realmshost.client.gui;

import link.botwmcs.samchai.realmshost.capability.town.Town;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class TownInfoScreen extends Screen {
    private final Town town;
    private final Screen lastScreen;
    private final Boolean showBackground;
    public TownInfoScreen(Component component, Town town, boolean showBackground, Screen lastScreen) {
        super(component);
        this.town = town;
        this.showBackground = showBackground;
        this.lastScreen = lastScreen;
    }
}
