package iceCreamService.model;


import java.util.Objects;

public class RoleTrack {

    public String userId;
    public String teamId;
    public String role;

    public RoleTrack(String userId, String teamId, String role) {
        this.userId = userId;
        this.teamId = teamId;
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleTrack roleTrack = (RoleTrack) o;
        return Objects.equals(userId, roleTrack.userId) &&
                Objects.equals(teamId, roleTrack.teamId) &&
                Objects.equals(role, roleTrack.role);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, teamId, role);
    }
}
