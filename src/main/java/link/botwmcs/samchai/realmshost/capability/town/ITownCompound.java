package link.botwmcs.samchai.realmshost.capability.town;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ITownCompound extends ComponentV3 {
    Town getTown(String townName);
    Map<String, Town> getAllTowns();
    List<UUID> getResidentUUIDs(Town town);
    List<ChunkPos> getTownClaimedChunks(Town town);
    UUID getOwner(Town town);
    String getTownName(Town town);
    String getTownComment(Town town);
    String getTownWorldLevel(Town town);
    boolean isPublic(Town town);
    boolean isOpen(Town town);
    boolean isStared(Town town);
    int getTownLevel(Town town);
    int getTownFunds(Town town);

    void setTownName(Town town, String townName);
    void setTownComment(Town town, String townComment);
    void setOwner(Town town, UUID owner);
    void setPublic(Town town, boolean isPublic);
    void setOpen(Town town, boolean isOpen);
    void setStared(Town town, boolean isStared);
    void setTownLevel(Town town, int townLevel);
    void setTownFunds(Town town, int townFunds);
    void setTownSpawn(Town town, BlockPos pos);
    void setTownHall(Town town, BlockPos pos);
    void setTownMarket(Town town, BlockPos pos);
    void setTownBank(Town town, BlockPos pos);
    void setTownJobBoard(Town town, BlockPos pos);
    void setTownYard(Town town, BlockPos pos);
    void setResidentUUIDs(Town town, List<UUID> residentUUIDs);
    void setTownClaimedChunks(Town town, List<ChunkPos> townClaimedChunks);

    void addTown(Town town);
    void removeTown(Town town);

}
