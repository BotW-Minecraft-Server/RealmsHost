package link.botwmcs.samchai.realmshost.capability;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;

public class Friend {
    public UUID friendUUID;
    public boolean isOnline;

    public Friend(UUID friendUUID, boolean isOnline) {
        this.friendUUID = friendUUID;
        this.isOnline = isOnline;
    }
}
