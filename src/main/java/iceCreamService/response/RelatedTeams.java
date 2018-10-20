package iceCreamService.response;

import iceCreamService.model.RoleTrack;

import java.util.List;

public class RelatedTeams {
    private List<RoleTrack> allTeamsOfUser;

    public RelatedTeams(List<RoleTrack> allTeamsOfUser) {
        this.allTeamsOfUser = allTeamsOfUser;
    }
}
