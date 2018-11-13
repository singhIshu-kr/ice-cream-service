package iceCreamService.response;

import iceCreamService.model.Role;

import java.util.List;

public class RelatedTeams {
    public List<Role> allTeamsOfUser;

    public RelatedTeams(List<Role> allTeamsOfUser) {
        this.allTeamsOfUser = allTeamsOfUser;
    }
}
