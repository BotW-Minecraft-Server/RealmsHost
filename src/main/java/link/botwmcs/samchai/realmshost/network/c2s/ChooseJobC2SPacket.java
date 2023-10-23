package link.botwmcs.samchai.realmshost.network.c2s;

import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record ChooseJobC2SPacket(GameProfile profile, String jobName) implements FabricPacket {
    public static final PacketType<ChooseJobC2SPacket> TYPE =
            PacketType.create(new ResourceLocation("c2s/choose_job"), ChooseJobC2SPacket::read);
    private static ChooseJobC2SPacket read(FriendlyByteBuf buf) {
        return new ChooseJobC2SPacket(buf.readGameProfile(), buf.readUtf());
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeGameProfile(profile);
        buf.writeUtf(jobName);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
