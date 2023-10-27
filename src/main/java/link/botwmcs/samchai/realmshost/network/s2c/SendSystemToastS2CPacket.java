package link.botwmcs.samchai.realmshost.network.s2c;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record SendSystemToastS2CPacket(String title, String subTitle) implements FabricPacket {

    public static final PacketType<SendSystemToastS2CPacket> TYPE =
            PacketType.create(new ResourceLocation("s2c/send_toast"), SendSystemToastS2CPacket::read);
    private static SendSystemToastS2CPacket read(FriendlyByteBuf buf) {
        return new SendSystemToastS2CPacket(buf.readUtf(), buf.readUtf());
    }
    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(title);
        buf.writeUtf(subTitle);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
