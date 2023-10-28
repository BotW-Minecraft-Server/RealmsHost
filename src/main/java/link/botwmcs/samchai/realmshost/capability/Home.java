package link.botwmcs.samchai.realmshost.capability;

import link.botwmcs.samchai.realmshost.RealmsHost;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Home {
    public ResourceKey<Level> homeLevel;
    public BlockPos homePos;
    public String homeName;

    public Home(ResourceKey<Level> homeLevel, BlockPos homePos, String homeName) {
        this.homeLevel = homeLevel;
        this.homePos = homePos;
        this.homeName = homeName;
    }
}
