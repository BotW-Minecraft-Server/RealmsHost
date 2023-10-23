package link.botwmcs.samchai.realmshost.network.s2c;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record OpenChooseJobScreenS2CPacket(boolean showBackground) implements FabricPacket {
    public static final PacketType<OpenChooseJobScreenS2CPacket> TYPE =
            PacketType.create(new ResourceLocation("s2c/open_choose_job_screen"), OpenChooseJobScreenS2CPacket::read);
    private static OpenChooseJobScreenS2CPacket read(FriendlyByteBuf buf) {
        return new OpenChooseJobScreenS2CPacket(buf.readBoolean());
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
