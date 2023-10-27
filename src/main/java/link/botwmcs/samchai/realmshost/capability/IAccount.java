package link.botwmcs.samchai.realmshost.capability;

import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;

public interface IAccount extends PlayerComponent {
    String getPlayerJob();
    String getPlayerTown();
    Integer getPlayerJobXp();
    Boolean isPlayerFirstJoinServer();


    void setPlayerJob(String playerJob);
    void setPlayerTown(String playerTown);
    void setPlayerJobXp(Integer playerJobXp);
    void setPlayerFirstJoinServer(Boolean playerFirstJoinServer);
}
