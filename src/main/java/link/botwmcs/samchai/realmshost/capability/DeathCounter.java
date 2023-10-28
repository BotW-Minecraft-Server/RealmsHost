package link.botwmcs.samchai.realmshost.capability;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;


public class DeathCounter {
    public ResourceKey<Level> deathLevel;
    public BlockPos deathPos;
    public long deathTime;

    public DeathCounter(ResourceKey<Level> deathLevel, BlockPos deathPos, long deathTime) {
        this.deathLevel = deathLevel;
        this.deathPos = deathPos;
        this.deathTime = deathTime;
    }
}
