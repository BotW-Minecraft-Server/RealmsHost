package link.botwmcs.samchai.realmshost.network.c2s;

import link.botwmcs.samchai.realmshost.RealmsHost;
import link.botwmcs.samchai.realmshost.capability.town.Town;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.RegistryLayer;
import net.minecraft.world.level.Level;

import java.util.UUID;

public record RespawnPlayerOnPlaceC2SPacket(ResourceKey<Level> level, BlockPos pos) implements FabricPacket {
    public static final PacketType<RespawnPlayerOnPlaceC2SPacket> TYPE =
            PacketType.create(new ResourceLocation("c2s/respawn_player_on_place"), RespawnPlayerOnPlaceC2SPacket::read);
    private static RespawnPlayerOnPlaceC2SPacket read(FriendlyByteBuf buf) {
        return new RespawnPlayerOnPlaceC2SPacket(buf.readResourceKey(Registries.DIMENSION), buf.readBlockPos());
    }
    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeResourceKey(level);
        buf.writeBlockPos(pos);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
