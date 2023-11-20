package link.botwmcs.samchai.realmshost.client;

import link.botwmcs.samchai.realmshost.client.gui.components.PlayerHUD;
import link.botwmcs.samchai.realmshost.network.S2CHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class RealmsHostClient implements ClientModInitializer {
    @Override
    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
        loadS2CNetworkPackets();
        loadHUD();
    }

    private static final String __OBFID = "CL_00003840";
    private void loadS2CNetworkPackets() {
        S2CHandler.register();
    }

    private void loadHUD() {
        HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> {
            PlayerHUD.getInstance().tick();
            PlayerHUD.getInstance().render(matrixStack, tickDelta);
        });
    }


}
