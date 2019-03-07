package iceCreamService.model;


import org.springframework.data.annotation.Id;

import java.util.Objects;

public class Role {

    @Id
    public String id;
    public String userId;
    public String teamId;
    public String role;

    public Role(String userId, String teamId, String role) {
        this.userId = userId;
        this.teamId = teamId;
        this.role = role;
    }

    public void setRole(String role) {
        this.role = role;
        System.out.println(this.role + role + "+++++++++++++++++");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role1 = (Role) o;
        return Objects.equals(userId, role1.userId) &&
                Objects.equals(teamId, role1.teamId) &&
                Objects.equals(role, role1.role);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, teamId, role);
    }

    @Override
    public String toString() {
        return "Role{" +
                "userId='" + userId + '\'' +
                ", teamId='" + teamId + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
