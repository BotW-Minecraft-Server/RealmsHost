package link.botwmcs.samchai.realmshost.network.s2c;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record OpenChooseTownScreenS2CPacket(boolean showBackground) implements FabricPacket {
    public static final PacketType<OpenChooseTownScreenS2CPacket> TYPE =
            PacketType.create(new ResourceLocation("s2c/open_choose_town_screen"), OpenChooseTownScreenS2CPacket::read);
    private static OpenChooseTownScreenS2CPacket read(FriendlyByteBuf buf) {
        return new OpenChooseTownScreenS2CPacket(buf.readBoolean());
    }
    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBoolean(showBackground);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
