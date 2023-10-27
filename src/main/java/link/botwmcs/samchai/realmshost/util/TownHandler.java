package link.botwmcs.samchai.realmshost.util;

import link.botwmcs.samchai.realmshost.RealmsHost;
import link.botwmcs.samchai.realmshost.capability.town.Town;
import link.botwmcs.samchai.realmshost.capability.town.TownCompoundHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.*;

public class TownHandler {
    public static Map<String, Town> getTowns(Level world) {
        TownCompoundHandler.TOWN_COMPONENT_KEY.sync(world);
        return world.getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).getAllTowns();
    }

    public static List<Town> getTownList(Level world) {
        return new ArrayList<>(getTowns(world).values());
    }

    public static void createTown(Level world, Player owner, String townName, String townComment, boolean isPublic, boolean isOpen, boolean isStared, int townLevel, int townFunds, BlockPos townSpawn, BlockPos townHall, BlockPos townMarket, BlockPos townBank, BlockPos townJobBoard, BlockPos townYard) {
        List<UUID> residentUUIDs = new ArrayList<>();
        residentUUIDs.add(owner.getUUID());
        List<ChunkPos> townClaimedChunks = new ArrayList<>();
        townClaimedChunks.add(PlayerUtilities.getPlayerChunkPos(owner));
        Town town = new Town(townName, townComment, owner.getDimensions(owner.getPose()).toString(), owner.getUUID(), isPublic, isOpen, isStared, townLevel, townFunds, townSpawn, townHall, townMarket, townBank, townJobBoard, townYard, residentUUIDs, townClaimedChunks);

        world.getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).addTown(town);
        TownCompoundHandler.TOWN_COMPONENT_KEY.sync(world);
        if (!world.isClientSide()) {
            ServerUtilities.saveAll(Objects.requireNonNull(world.getServer()));
        }
    }

    public static boolean removeTown(Level world, Town town) {
        if (world.getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).getAllTowns().containsValue(town)) {
            world.getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).removeTown(town);
            TownCompoundHandler.TOWN_COMPONENT_KEY.sync(world);
            return true;
        } else {
            return false;
        }
    }

    public static boolean removeTownByName(Level world, String townName) {
        if (world.getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).getAllTowns().containsKey(townName)) {
            Town town = world.getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).getTown(townName);
            removeTown(world, town);
            return true;
        } else {
            return false;
        }
    }

    public static boolean addTownClaimedChunks(Town town, ChunkPos townClaimedChunk) {
        if (!town.townClaimedChunks.contains(townClaimedChunk)) {
            town.addClaimedChunk(townClaimedChunk);
            return true;
        }
        return false;
    }

    public static boolean removeTownClaimedChunks(Town town, ChunkPos townClaimedChunk) {
        if (town.townClaimedChunks.contains(townClaimedChunk)) {
            town.removeClaimedChunk(townClaimedChunk);
            return true;
        }
        return false;
    }

    public static boolean addTownResidents(Town town, ServerPlayer resident) {
        if (!town.residentUUIDs.contains(resident.getUUID())) {
            town.addResident(resident);
            return true;
        }
        return false;
    }

    public static boolean removeTownResidents(Town town, ServerPlayer resident) {
        if (town.residentUUIDs.contains(resident.getUUID())) {
            town.removeResident(resident);
            return true;
        }
        return false;
    }
}
