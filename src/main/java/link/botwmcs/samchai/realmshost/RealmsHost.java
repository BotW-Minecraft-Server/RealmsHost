package link.botwmcs.samchai.realmshost;

import com.mojang.logging.LogUtils;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import link.botwmcs.samchai.realmshost.command.*;
import link.botwmcs.samchai.realmshost.config.ServerConfig;
import link.botwmcs.samchai.realmshost.event.player.*;
import link.botwmcs.samchai.realmshost.event.server.ReloadEvent;
import link.botwmcs.samchai.realmshost.network.C2SHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraftforge.fml.config.ModConfig;
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
        loadConfig();
    }

    private void printConsoleOutput() {
        LOGGER.info("RealmsHost is loaded!");
    }
    private void loadEvents() {
        PlayerEventHandler.PLAYER_LOGGED_OUT_EVENT.register(WorldLeaveEvent::onLoggedOut);
        PlayerEventHandler.PLAYER_LOGGED_IN_EVENT.register(WorldJoinEvent::onLoggedIn);
        PlayerEventHandler.PLAYER_DEATH_EVENT.register(PlayerDeathEvent::onPlayerDeath);
        UseEntityCallback.EVENT.register(UsePlayerEvent::onUsePlayer);
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register(ReloadEvent::onReload);
    }
    private void loadCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            ManageCommand.register(dispatcher);
            TownCommand.register(dispatcher);
            AnnounceCommand.register(dispatcher);
            HomeCommand.register(dispatcher);
        });
    }
    private void loadC2SNetworkPackets() {
        C2SHandler.register();
    }
    private void loadConfig() {
        ForgeConfigRegistry.INSTANCE.register(RealmsHost.MODID, ModConfig.Type.SERVER, ServerConfig.CONFIG_SPEC);
    }
}
