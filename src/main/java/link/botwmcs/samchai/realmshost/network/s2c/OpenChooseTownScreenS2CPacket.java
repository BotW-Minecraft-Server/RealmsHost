package link.botwmcs.samchai.realmshost.network.s2c;

import link.botwmcs.samchai.realmshost.capability.town.Town;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record OpenChooseTownScreenS2CPacket(List<Town> townList, boolean showBackground) implements FabricPacket {
    public static final PacketType<OpenChooseTownScreenS2CPacket> TYPE =
            PacketType.create(new ResourceLocation("s2c/open_choose_town_screen"), OpenChooseTownScreenS2CPacket::read);
    private static OpenChooseTownScreenS2CPacket read(FriendlyByteBuf buf) {
        List<Town> towns = new ArrayList<>();
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            CompoundTag townTag = buf.readNbt();
            ListTag claimedChunksTag = townTag.getList("claimedChunks", 10);
            ListTag residentTag = townTag.getList("residents", 11);
            List<UUID> residentUUIDs = new ArrayList<>();
            List<ChunkPos> townClaimedChunks = new ArrayList<>();
            for (Tag uuidTag : residentTag) {
                residentUUIDs.add(NbtUtils.loadUUID(uuidTag));
            }
            for (Tag posTag : claimedChunksTag) {
                CompoundTag chunkTag = (CompoundTag) posTag;
                int x = chunkTag.getInt("X");
                int z = chunkTag.getInt("Z");
                ChunkPos chunkPos = new ChunkPos(x, z);
                townClaimedChunks.add(chunkPos);
            }
            Town town = new Town(
                    townTag.getString("townName"),
                    townTag.getString("townComment"),
                    townTag.getString("townWorldLevel"),
                    townTag.getUUID("owner"),
                    townTag.getBoolean("isPublic"),
                    townTag.getBoolean("isOpen"),
                    townTag.getBoolean("isStared"),
                    townTag.getInt("townLevel"),
                    townTag.getInt("townFunds"),
                    NbtUtils.readBlockPos(townTag.getCompound("townSpawn")),
                    NbtUtils.readBlockPos(townTag.getCompound("townHall")),
                    NbtUtils.readBlockPos(townTag.getCompound("townMarket")),
                    NbtUtils.readBlockPos(townTag.getCompound("townBank")),
                    NbtUtils.readBlockPos(townTag.getCompound("townJobBoard")),
                    NbtUtils.readBlockPos(townTag.getCompound("townYard")),
                    residentUUIDs,
                    townClaimedChunks
            );
            towns.add(town);
        }
        return new OpenChooseTownScreenS2CPacket(towns, buf.readBoolean());
    }
    @Override
    public void write(FriendlyByteBuf buf) {
        List<Town> towns = townList;
        buf.writeInt(towns.size());
        for (Town town : towns) {
            CompoundTag townTag = new CompoundTag();
            townTag.putString("townName", town.townName);
            townTag.putString("townComment", town.townComment);
            townTag.putString("townWorldLevel", town.townWorldLevel);
            townTag.putUUID("owner", town.owner);
            townTag.putBoolean("isPublic", town.isPublic);
            ListTag residentTag = new ListTag();
            for (UUID uuid : town.residentUUIDs) {
                residentTag.add(NbtUtils.createUUID(uuid));
            }
            townTag.put("residents", residentTag);
            townTag.putBoolean("isOpen", town.isOpen);
            townTag.putBoolean("isStared", town.isStared);
            townTag.putInt("townLevel", town.townLevel);
            townTag.putInt("townFunds", town.townFunds);
            ListTag claimedChunksTag = new ListTag();
            for (ChunkPos chunkPos : town.townClaimedChunks) {
                CompoundTag chunkTag = new CompoundTag();
                chunkTag.putInt("X", chunkPos.x);
                chunkTag.putInt("Z", chunkPos.z);
                claimedChunksTag.add(chunkTag);
            }
            townTag.put("claimedChunks", claimedChunksTag);
            townTag.put("townSpawn", NbtUtils.writeBlockPos(town.townSpawn));
            townTag.put("townHall", NbtUtils.writeBlockPos(town.townHall));
            townTag.put("townMarket", NbtUtils.writeBlockPos(town.townMarket));
            townTag.put("townBank", NbtUtils.writeBlockPos(town.townBank));
            townTag.put("townJobBoard", NbtUtils.writeBlockPos(town.townJobBoard));
            townTag.put("townYard", NbtUtils.writeBlockPos(town.townYard));
            buf.writeNbt(townTag);
        }
        buf.writeBoolean(showBackground);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
