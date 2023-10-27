package link.botwmcs.samchai.realmshost.capability.town;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.CopyableComponent;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.ChunkPos;

import java.util.*;

public class BaseTownCompound implements ITownCompound, Component, CopyableComponent<ITownCompound> {
    public final Map<String, Town> towns = new HashMap<>();


    public BaseTownCompound() {}
    @Override
    public Town getTown(String townName) {
        return towns.get(townName);
    }

    @Override
    public Map<String, Town> getAllTowns() {
        return towns;
    }

    @Override
    public List<UUID> getResidentUUIDs(Town town) {
        return town.residentUUIDs;
    }

    @Override
    public List<ChunkPos> getTownClaimedChunks(Town town) {
        return town.townClaimedChunks;
    }

    @Override
    public UUID getOwner(Town town) {
        return town.owner;
    }

    @Override
    public String getTownName(Town town) {
        return town.townName;
    }

    @Override
    public String getTownComment(Town town) {
        return town.townComment;
    }

    @Override
    public String getTownWorldLevel(Town town) {
        return town.townWorldLevel;
    }

    @Override
    public boolean isPublic(Town town) {
        return town.isPublic;
    }

    @Override
    public boolean isOpen(Town town) {
        return town.isOpen;
    }

    @Override
    public boolean isStared(Town town) {
        return town.isStared;
    }

    @Override
    public int getTownLevel(Town town) {
        return town.townLevel;
    }

    @Override
    public int getTownFunds(Town town) {
        return town.townFunds;
    }

    @Override
    public void setTownName(Town town, String townName) {
        town.townName = townName;
    }

    @Override
    public void setTownComment(Town town, String townComment) {
        town.townComment = townComment;
    }

    @Override
    public void setOwner(Town town, UUID owner) {
        town.owner = owner;
    }

    @Override
    public void setPublic(Town town, boolean isPublic) {
        town.isPublic = isPublic;
    }

    @Override
    public void setOpen(Town town, boolean isOpen) {
        town.isOpen = isOpen;
    }

    @Override
    public void setStared(Town town, boolean isStared) {
        town.isStared = isStared;
    }

    @Override
    public void setTownLevel(Town town, int townLevel) {
        town.townLevel = townLevel;
    }

    @Override
    public void setTownFunds(Town town, int townFunds) {
        town.townFunds = townFunds;
    }

    @Override
    public void setTownSpawn(Town town, BlockPos pos) {
        town.townSpawn = pos;
    }

    @Override
    public void setTownHall(Town town, BlockPos pos) {
        town.townHall = pos;
    }

    @Override
    public void setTownMarket(Town town, BlockPos pos) {
        town.townMarket = pos;
    }

    @Override
    public void setTownBank(Town town, BlockPos pos) {
        town.townBank = pos;
    }

    @Override
    public void setTownJobBoard(Town town, BlockPos pos) {
        town.townJobBoard = pos;
    }

    @Override
    public void setTownYard(Town town, BlockPos pos) {
        town.townYard = pos;
    }

    @Override
    public void setResidentUUIDs(Town town, List<UUID> residentUUIDs) {
        town.residentUUIDs = residentUUIDs;
    }

    @Override
    public void setTownClaimedChunks(Town town, List<ChunkPos> townClaimedChunks) {
        town.townClaimedChunks = townClaimedChunks;
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

            townList.add(townTag);
        }
        tag.put("towns", townList);
    }

    @Override
    public void copyFrom(ITownCompound other) {
        towns.clear();
        for (Town town : other.getAllTowns().values()) {
            towns.put(town.townName, town);
        }
    }
}
