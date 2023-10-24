package link.botwmcs.samchai.realmshost.capability.town;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;

public interface ITownCompound extends ComponentV3 {
    Town getTown(String townName);
    void addTown(Town town);
    void removeTown(Town town);

}
