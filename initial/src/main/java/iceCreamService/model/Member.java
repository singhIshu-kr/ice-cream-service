package iceCreamService.model;

import org.springframework.data.annotation.Id;

import java.util.Objects;

public class Member {

    @Id
    public String id;
    public final String name;
    public String teamID;

    public Member(String name,String teamID) {
        this.name = name;
        this.teamID = teamID;
    }

    public void setTeamId(String teamId) {
        this.teamID = teamID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id) &&
                Objects.equals(name, member.name) &&
                Objects.equals(teamID, member.teamID);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, teamID);
    }
}
