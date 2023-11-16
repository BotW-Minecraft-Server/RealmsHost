package link.botwmcs.samchai.realmshost.event.server;

import link.botwmcs.samchai.realmshost.RealmsHost;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.CloseableResourceManager;

public class ReloadEvent {
    public static void onReload(MinecraftServer server, CloseableResourceManager closeableResourceManager, boolean b) {
        // todo
        RealmsHost.LOGGER.info("Triggered RealmsHost reload.");

    }
}
