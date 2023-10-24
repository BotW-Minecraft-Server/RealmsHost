package link.botwmcs.samchai.realmshost.capability.town;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;

import java.util.Map;

public interface ITownCompound extends ComponentV3 {
    Town getTown(String townName);
    Map<String, Town> getAllTowns();
    void addTown(Town town);
    void removeTown(Town town);

}
