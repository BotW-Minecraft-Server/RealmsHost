package link.botwmcs.samchai.realmshost.client;

import link.botwmcs.samchai.realmshost.network.S2CHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class RealmsHostClient implements ClientModInitializer {
    @Override
    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
        loadS2CNetworkPackets();
    }

    private static final String __OBFID = "CL_00003840";
    private void loadS2CNetworkPackets() {
        S2CHandler.register();
    }


}
