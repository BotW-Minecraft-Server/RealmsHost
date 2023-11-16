package link.botwmcs.samchai.realmshost.network.s2c;

import link.botwmcs.samchai.realmshost.capability.PlayerInfo;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record OpenPlayerInfoScreenS2CPacket(PlayerInfo playerInfo, boolean showBackground) implements FabricPacket {
    public static final PacketType<OpenPlayerInfoScreenS2CPacket> TYPE =
            PacketType.create(new ResourceLocation("s2c/open_player_info_screen"), OpenPlayerInfoScreenS2CPacket::read);
    private static OpenPlayerInfoScreenS2CPacket read(FriendlyByteBuf buf) {
        CompoundTag playerInfoTag = buf.readNbt();
        ListTag playerFriendUUIDsTag = playerInfoTag.getList("playerFriendUUIDs", 11);
        List<UUID> playerFriendUUIDs = new ArrayList<>();
        for (Tag uuidTag : playerFriendUUIDsTag) {
            playerFriendUUIDs.add(NbtUtils.loadUUID(uuidTag));
        }
        PlayerInfo playerInfo = new PlayerInfo(
                playerInfoTag.getString("playerName"),
                playerInfoTag.getInt("playerVanillaXp"),
                playerInfoTag.getString("playerJob"),
                playerInfoTag.getInt("playerJobXp"),
                playerInfoTag.getString("playerTown"),
                playerFriendUUIDs,
                playerInfoTag.getInt("playerMoney"),
                playerInfoTag.getInt("playerOnlineTimer")
        );
        return new OpenPlayerInfoScreenS2CPacket(playerInfo, buf.readBoolean());
    }
    @Override
    public void write(FriendlyByteBuf buf) {
        CompoundTag playerInfoTag = new CompoundTag();
        playerInfoTag.putString("playerName", playerInfo.playerName);
        playerInfoTag.putInt("playerVanillaXp", playerInfo.playerVanillaXp);
        playerInfoTag.putString("playerJob", playerInfo.playerJob);
        playerInfoTag.putInt("playerJobXp", playerInfo.playerJobXp);
        playerInfoTag.putString("playerTown", playerInfo.playerTown);
        ListTag playerFriendUUIDsTag = new ListTag();
        for (UUID uuid : playerInfo.playerFriendUUIDs) {
            playerFriendUUIDsTag.add(NbtUtils.createUUID(uuid));
        }
        playerInfoTag.put("playerFriendUUIDs", playerFriendUUIDsTag);
        playerInfoTag.putInt("playerMoney", playerInfo.playerMoney);
        playerInfoTag.putInt("playerOnlineTimer", playerInfo.playerOnlineTimer);
        buf.writeNbt(playerInfoTag);
        buf.writeBoolean(showBackground);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
