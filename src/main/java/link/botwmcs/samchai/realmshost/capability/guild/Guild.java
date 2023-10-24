package link.botwmcs.samchai.realmshost.capability.guild;

import link.botwmcs.samchai.realmshost.capability.town.Town;

import java.util.ArrayList;
import java.util.List;

public class Guild {
    public String guildName;
    public String guildComment;
    public boolean isPublic;
    public List<Town> towns = new ArrayList<>();

    public Guild(String guildName, String guildComment, boolean isPublic) {
        this.guildName = guildName;
        this.guildComment = guildComment;
        this.isPublic = isPublic;
    }

    public void addTown(Town town) {
        towns.add(town);
        for (Town town1 : towns) {
            if (town1.isPublic && !this.isPublic) {
                this.isPublic = true;
                break;
            }
        }
    }
    public void removeTown(Town town) {
        towns.remove(town);
    }
}
