package link.botwmcs.samchai.realmshost.client.gui;

import link.botwmcs.samchai.realmshost.capability.PlayerInfo;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class PlayerInfoScreen extends Screen {
    public final PlayerInfo playerInfo;
    public final boolean showBackground;
    public PlayerInfoScreen(Component component, PlayerInfo playerInfo, boolean showBackground) {
        super(component);
        this.playerInfo = playerInfo;
        this.showBackground = showBackground;
    }
}
