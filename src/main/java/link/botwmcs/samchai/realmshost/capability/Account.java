package link.botwmcs.samchai.realmshost.capability;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.nbt.CompoundTag;

public class Account implements IAccount, AutoSyncedComponent {
    private String playerJob;
    private String playerTown;
    private Integer playerJobXp;
    private boolean playerFirstJoinServer = true;
    private final Object provider;
    public Account(Object provider) {
        this.provider = provider;
    }

    @Override
    public String getPlayerJob() {
        if (playerJob == null) {
            return "default";
        }
        return playerJob;
    }

    @Override
    public String getPlayerTown() {
        if (playerTown == null) {
            return "default";
        }
        return playerTown;
    }

    @Override
    public Integer getPlayerJobXp() {
        if (playerJobXp == null) {
            return 0;
        }
        return playerJobXp;
    }

    @Override
    public Boolean isPlayerFirstJoinServer() {
        return playerFirstJoinServer;
    }

    @Override
    public void setPlayerJob(String playerJob) {
        this.playerJob = playerJob;
        AccountHandler.ACCOUNT_COMPONENT_KEY.sync(this.provider);
    }

    @Override
    public void setPlayerTown(String playerTown) {
        this.playerTown = playerTown;
        AccountHandler.ACCOUNT_COMPONENT_KEY.sync(this.provider);
    }

    @Override
    public void setPlayerJobXp(Integer playerJobXp) {
        this.playerJobXp = playerJobXp;
        AccountHandler.ACCOUNT_COMPONENT_KEY.sync(this.provider);
    }

    @Override
    public void setPlayerFirstJoinServer(Boolean playerFirstJoinServer) {
        this.playerFirstJoinServer = playerFirstJoinServer;
        AccountHandler.ACCOUNT_COMPONENT_KEY.sync(this.provider);

    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        playerJob = tag.getString("playerJob");
        playerTown = tag.getString("playerTown");
        playerJobXp = tag.getInt("playerJobXp");
        playerFirstJoinServer = tag.getBoolean("playerFirstJoinServer");
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        if (playerJob == null) {
            playerJob = "default";
        }
        tag.putString("playerJob", playerJob);

        if (playerTown == null) {
            playerTown = "default";
        }
        tag.putString("playerTown", playerTown);

        if (playerJobXp == null) {
            playerJobXp = 0;
        }
        tag.putInt("playerJobXp", playerJobXp);

        tag.putBoolean("playerFirstJoinServer", playerFirstJoinServer);
    }
}
