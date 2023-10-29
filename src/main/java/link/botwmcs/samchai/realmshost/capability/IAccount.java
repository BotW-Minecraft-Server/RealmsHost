package link.botwmcs.samchai.realmshost.capability;

import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;

import java.util.List;

public interface IAccount extends PlayerComponent {
    String getPlayerJob();
    String getPlayerTown();
    Integer getPlayerJobXp();
    Boolean isPlayerFirstJoinServer();
    List<DeathCounter> getDeathCounterList();
    List<Home> getHomeList();
    List<Friend> getFriendList();


    void setPlayerJob(String playerJob);
    void setPlayerTown(String playerTown);
    void setPlayerJobXp(Integer playerJobXp);
    void setPlayerFirstJoinServer(Boolean playerFirstJoinServer);
    void setDeathCounterList(List<DeathCounter> counterList);
    void setHomeList(List<Home> homeList);
    void setFriendList(List<Friend> friendList);
}
