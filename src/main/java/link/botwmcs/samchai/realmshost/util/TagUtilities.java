package link.botwmcs.samchai.realmshost.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.ChunkPos;

public class TagUtilities {
    public static CompoundTag writeChunkPosToTag(ChunkPos chunkPos) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("x", chunkPos.x);
        tag.putInt("z", chunkPos.z);
        return tag;
    }
    public static ChunkPos readChunkPosFromTag(CompoundTag tag) {
        return new ChunkPos(tag.getInt("x"), tag.getInt("z"));
    }
}
