package link.botwmcs.samchai.realmshost.util;

import link.botwmcs.samchai.realmshost.capability.town.Town;
import link.botwmcs.samchai.realmshost.capability.town.TownCompoundHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TownHandler {
    public static Map<String, Town> getTowns(Level world) {
        return world.getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).getAllTowns();
    }
    public static List<Town> getTownList(Level world) {
        return new ArrayList<>(world.getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).getAllTowns().values());
    }
    public static boolean removeTown(Level world, Town town) {
        if (world.getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).getAllTowns().containsValue(town)) {
            world.getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).removeTown(town);
            return true;
        } else {
            return false;
        }
    }
    public static boolean removeTownByName(Level world, String townName) {
        if (world.getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).getAllTowns().containsKey(townName)) {
            Town town = world.getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).getTown(townName);
            world.getComponent(TownCompoundHandler.TOWN_COMPONENT_KEY).removeTown(town);
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
