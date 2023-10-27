package link.botwmcs.samchai.realmshost;

import com.mojang.logging.LogUtils;
import link.botwmcs.samchai.realmshost.command.ManageCommand;
import link.botwmcs.samchai.realmshost.command.ToastCommand;
import link.botwmcs.samchai.realmshost.command.TownCommand;
import link.botwmcs.samchai.realmshost.event.player.PlayerEventHandler;
import link.botwmcs.samchai.realmshost.event.player.WorldJoinEvent;
import link.botwmcs.samchai.realmshost.event.player.WorldLeaveEvent;
import link.botwmcs.samchai.realmshost.network.C2SHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;

public class RealmsHost implements ModInitializer {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MODID = "realmshost";
    @Override
    public void onInitialize() {
        printConsoleOutput();
        loadEvents();
        loadCommands();
        loadC2SNetworkPackets();
    }

    private void printConsoleOutput() {
        LOGGER.info("RealmsHost is loaded!");
    }
    private void loadEvents() {
        PlayerEventHandler.PLAYER_LOGGED_OUT_EVENT.register(WorldLeaveEvent::onLoggedOut);
        PlayerEventHandler.PLAYER_LOGGED_IN_EVENT.register(WorldJoinEvent::onLoggedIn);
    }
    private void loadCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            ManageCommand.register(dispatcher);
            TownCommand.register(dispatcher);
            ToastCommand.register(dispatcher);
        });
    }
    private void loadC2SNetworkPackets() {
        C2SHandler.register();
    }
}
