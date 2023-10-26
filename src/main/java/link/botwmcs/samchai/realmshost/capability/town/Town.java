package link.botwmcs.samchai.realmshost.capability.town;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;

import java.util.*;

public class Town {
    public List<UUID> residentUUIDs = new ArrayList<>();
    public List<ChunkPos> townClaimedChunks = new ArrayList<>();
    public UUID owner;
    public String townName;
    public String townComment;
    public boolean isPublic;
    public boolean isOpen;
    public boolean isStared;
    public int townLevel;
    public int townFunds;
    public BlockPos townSpawn;
    public BlockPos townHall;
    public BlockPos townMarket;
    public BlockPos townBank;
    public BlockPos townJobBoard;
    public BlockPos townYard;

    public Town(String townName, String townComment, UUID owner, boolean isPublic, boolean isOpen, boolean isStared, int townLevel, int townFunds, BlockPos townSpawn, BlockPos townHall, BlockPos townMarket, BlockPos townBank, BlockPos townJobBoard, BlockPos townYard) {
        this.townName = townName;
        this.townComment = townComment;
        this.owner = owner;
        this.isPublic = isPublic;
        this.isOpen = isOpen;
        this.isStared = isStared;
        this.townLevel = townLevel;
        this.townFunds = townFunds;
        this.townSpawn = townSpawn;
        this.townHall = townHall;
        this.townMarket = townMarket;
        this.townBank = townBank;
        this.townJobBoard = townJobBoard;
        this.townYard = townYard;
    }

    public void addResident(ServerPlayer serverPlayer) {
        residentUUIDs.add(serverPlayer.getUUID());
    }
    public void removeResident(ServerPlayer serverPlayer) {
        residentUUIDs.remove(serverPlayer.getUUID());
    }
    public void setTownName(String townName) {
        this.townName = townName;
    }
    public void setTownComment(String townComment) {
        this.townComment = townComment;
    }
    public void setOwner(ServerPlayer owner) {
        this.owner = owner.getUUID();
    }
    public void setTownLevel(int townLevel) {
        this.townLevel = townLevel;
    }
    public void setTownFunds(int townFunds) {
        this.townFunds = townFunds;
    }
    public void setTownSpawn(BlockPos townSpawn) {
        this.townSpawn = townSpawn;
    }
    public void setTownHall(BlockPos townHall) {
        this.townHall = townHall;
    }
    public void setTownMarket(BlockPos townMarket) {
        this.townMarket = townMarket;
    }
    public void setTownBank(BlockPos townBank) {
        this.townBank = townBank;
    }
    public void setTownJobBoard(BlockPos townJobBoard) {
        this.townJobBoard = townJobBoard;
    }
    public void setTownYard(BlockPos townYard) {
        this.townYard = townYard;
    }
    public void addClaimedChunk(ChunkPos chunkPos) {
        townClaimedChunks.add(chunkPos);
    }
    public void removeClaimedChunk(ChunkPos chunkPos) {
        townClaimedChunks.remove(chunkPos);
    }
    public int getClaimedChunks() {
        return townClaimedChunks.size();
    }
}
