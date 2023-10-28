package link.botwmcs.samchai.realmshost.capability;

import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;

import java.util.List;

public interface IAccount extends PlayerComponent {
    String getPlayerJob();
    String getPlayerTown();
    Integer getPlayerJobXp();
    Boolean isPlayerFirstJoinServer();
    DeathCounter getDeathCounter(int index);
    List<DeathCounter> getDeathCounterList();


    void setPlayerJob(String playerJob);
    void setPlayerTown(String playerTown);
    void setPlayerJobXp(Integer playerJobXp);
    void setPlayerFirstJoinServer(Boolean playerFirstJoinServer);
    void setDeathCounter(DeathCounter deathCounter, int index);
    void setDeathCounterList(List<DeathCounter> counterList);
    void addDeathCounter(DeathCounter deathCounter);
}
