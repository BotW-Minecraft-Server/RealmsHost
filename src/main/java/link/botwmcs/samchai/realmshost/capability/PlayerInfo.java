package link.botwmcs.samchai.realmshost.capability;

import java.util.List;
import java.util.UUID;

public class PlayerInfo {
    public String playerName;
    public int playerVanillaXp;
    public String playerJob;
    public int playerJobXp;
    public String playerTown;
    public List<UUID> playerFriendUUIDs;
    public int playerMoney;
    public int playerOnlineTimer;


    public PlayerInfo(String playerName, int playerVanillaXp, String playerJob, int playerJobXp, String playerTown, List<UUID> playerFriendUUIDs, int playerMoney, int playerOnlineTimer) {
        this.playerName = playerName;
        this.playerVanillaXp = playerVanillaXp;
        this.playerJob = playerJob;
        this.playerJobXp = playerJobXp;
        this.playerTown = playerTown;
        this.playerFriendUUIDs = playerFriendUUIDs;
        this.playerMoney = playerMoney;
        this.playerOnlineTimer = playerOnlineTimer;
    }
}
