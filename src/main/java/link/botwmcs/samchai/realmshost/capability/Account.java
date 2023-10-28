package link.botwmcs.samchai.realmshost.capability;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import link.botwmcs.samchai.realmshost.RealmsHost;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class Account implements IAccount, AutoSyncedComponent {
    private String playerJob;
    private String playerTown;
    private Integer playerJobXp;
    private boolean playerFirstJoinServer = true;
    private List<DeathCounter> counterList;
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
    public DeathCounter getDeathCounter(int index) {
        return counterList.get(index);
    }

    @Override
    public List<DeathCounter> getDeathCounterList() {
        if (counterList == null) {
            return new ArrayList<>();
        }
        return counterList;
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
    public void setDeathCounter(DeathCounter deathCounter, int index) {
        this.counterList.set(index, deathCounter);
        AccountHandler.ACCOUNT_COMPONENT_KEY.sync(this.provider);
    }

    @Override
    public void setDeathCounterList(List<DeathCounter> counterList) {
        this.counterList = counterList;
        AccountHandler.ACCOUNT_COMPONENT_KEY.sync(this.provider);
    }

    @Override
    public void addDeathCounter(DeathCounter deathCounter) {
        this.counterList.add(deathCounter);
        RealmsHost.LOGGER.info(String.valueOf(this.counterList.size()));
        RealmsHost.LOGGER.info(String.valueOf(this.counterList.get(0).deathPos));
        AccountHandler.ACCOUNT_COMPONENT_KEY.sync(this.provider);
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        playerJob = tag.getString("playerJob");
        playerTown = tag.getString("playerTown");
        playerJobXp = tag.getInt("playerJobXp");
        playerFirstJoinServer = tag.getBoolean("playerFirstJoinServer");

        // Death counter
        if (tag.contains("deathCounter")) {
            if (counterList == null) {
                counterList = new ArrayList<>();
            } else {
                counterList.clear();
            }
            for (Tag t : tag.getList("deathCounter", 10)) {
                CompoundTag counterTag = (CompoundTag) t;
                DeathCounter deathCounter = new DeathCounter(
                        Level.RESOURCE_KEY_CODEC.parse(NbtOps.INSTANCE, counterTag.get("deathLevel")).resultOrPartial(RealmsHost.LOGGER::error).get(),
                        new BlockPos(
                                counterTag.getInt("locationPosX"),
                                counterTag.getInt("locationPosY"),
                                counterTag.getInt("locationPosZ")
                        ),
                        counterTag.getLong("deathTime")
                );
                counterList.add(deathCounter);
            }
            counterList.sort((o1, o2) -> (int) (o2.deathTime - o1.deathTime));
        }
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        // Job or sth
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

        // Death counter
        if (counterList == null) {
            counterList = new ArrayList<>();
        }
        ListTag deathCounterTag = new ListTag();
        for (DeathCounter counter : counterList) {
            CompoundTag counterTag = new CompoundTag();
            ResourceLocation.CODEC.encodeStart(NbtOps.INSTANCE, counter.deathLevel.location()).resultOrPartial(RealmsHost.LOGGER::error).ifPresent((lambda) -> {
                counterTag.put("deathLevel", lambda);
            });
            counterTag.putInt("locationPosX", counter.deathPos.getX());
            counterTag.putInt("locationPosY", counter.deathPos.getY());
            counterTag.putInt("locationPosZ", counter.deathPos.getZ());
            counterTag.putLong("deathTime", counter.deathTime);
            deathCounterTag.add(counterTag);
            if (deathCounterTag.size() >= 10) {
                deathCounterTag.remove(0);
            }
        }
        tag.put("deathCounter", deathCounterTag);

    }
}
