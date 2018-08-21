package iceCreamService.Domain;

import org.springframework.data.annotation.Id;

public class Member {

    @Id
    public final String id;
    private final String name;
    public String teamID;

    public Member(String id, String name,String teamID) {
        this.id = id;
        this.name = name;
        this.teamID = teamID;
    }

    public void setTeamId(String teamId) {
        this.teamID = teamID;
    }
}
