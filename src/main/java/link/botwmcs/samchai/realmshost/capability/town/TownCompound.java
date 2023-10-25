package link.botwmcs.samchai.realmshost.capability.town;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;

import java.util.*;

public class TownCompound implements ITownCompound, AutoSyncedComponent {
    private final Map<String, Town> towns = new HashMap<>();
    @Override
    public Town getTown(String townName) {
        return towns.get(townName);
    }

    @Override
    public Map<String, Town> getAllTowns() {
        return towns;
    }

    @Override
    public void addTown(Town town) {
        towns.put(town.townName, town);
    }

    @Override
    public void removeTown(Town town) {
        towns.remove(town.townName);
    }

    // NBT tag types for reference:
    // 0:  TAG_End
    // 1:  TAG_Byte
    // 2:  TAG_Short
    // 3:  TAG_Int
    // 4:  TAG_Long
    // 5:  TAG_Float
    // 6:  TAG_Double
    // 7:  TAG_Byte_Array
    // 8:  TAG_String
    // 9:  TAG_List
    // 10: TAG_Compound
    // 11: TAG_Int_Array
    // 12: TAG_Long_Array

    @Override
    public void readFromNbt(CompoundTag tag) {
        towns.clear();
        ListTag townList = tag.getList("towns", 10);
        for (Tag t : townList) {
            CompoundTag townTag = (CompoundTag) t;
            Town town = new Town(
                    townTag.getString("townName"),
                    townTag.getString("townComment"),
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
                    NbtUtils.readBlockPos(townTag.getCompound("townYard"))
            );
            town.townName = townTag.getString("townName");
            town.townComment = townTag.getString("townComment");
            town.isPublic = townTag.getBoolean("isPublic");
            ListTag residentTag = townTag.getList("residents", 11);
            for (Tag uuidTag : residentTag) {
                town.residentUUIDs.add(NbtUtils.loadUUID(uuidTag));
            }
            town.isOpen = townTag.getBoolean("isOpen");
            town.isStared = townTag.getBoolean("isStared");
            town.townLevel = townTag.getInt("townLevel");
            town.townFunds = townTag.getInt("townFunds");
            ListTag claimedChunksTag = townTag.getList("claimedChunks", 10);
            for (Tag posTag : claimedChunksTag) {
                CompoundTag chunkTag = (CompoundTag) posTag;
                int x = chunkTag.getInt("X");
                int z = chunkTag.getInt("Z");
                ChunkPos chunkPos = new ChunkPos(x, z);
                town.townClaimedChunks.add(chunkPos);
            }
            town.townSpawn = NbtUtils.readBlockPos(townTag.getCompound("townSpawn"));
            town.townHall = NbtUtils.readBlockPos(townTag.getCompound("townHall"));
            town.townMarket = NbtUtils.readBlockPos(townTag.getCompound("townMarket"));
            town.townBank = NbtUtils.readBlockPos(townTag.getCompound("townBank"));
            town.townJobBoard = NbtUtils.readBlockPos(townTag.getCompound("townJobBoard"));
            town.townYard = NbtUtils.readBlockPos(townTag.getCompound("townYard"));
            towns.put(town.townName, town);
        }
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        ListTag townList = new ListTag();
        for (Town town : towns.values()) {
            CompoundTag townTag = new CompoundTag();
            townTag.putString("townName", town.townName);
            townTag.putString("townComment", town.townComment);
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

            townList.add(townTag);
        }
        tag.put("towns", townList);
    }
}
