package link.botwmcs.samchai.realmshost.network.c2s;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record ChooseTownC2SPacket(String townName) implements FabricPacket {
    public static final PacketType<ChooseTownC2SPacket> TYPE =
            PacketType.create(new ResourceLocation("c2s/choose_town"), ChooseTownC2SPacket::read);
    private static ChooseTownC2SPacket read(FriendlyByteBuf buf) {
        return new ChooseTownC2SPacket(buf.readUtf());
    }
    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(townName);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
