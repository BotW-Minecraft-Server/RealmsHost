package link.botwmcs.samchai.realmshost.network.s2c;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record SendBossBarHudComponentS2CPacket(String component, int stayTime) implements FabricPacket {

    public static final PacketType<SendBossBarHudComponentS2CPacket> TYPE =
            PacketType.create(new ResourceLocation("s2c/send_bossbar_hud_component"), SendBossBarHudComponentS2CPacket::read);

    private static SendBossBarHudComponentS2CPacket read(FriendlyByteBuf buf) {
        return new SendBossBarHudComponentS2CPacket(buf.readUtf(), buf.readInt());
    }
    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(component);
        buf.writeInt(stayTime);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
