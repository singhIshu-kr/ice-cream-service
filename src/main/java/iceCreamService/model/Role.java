package iceCreamService.model;


import java.util.Objects;

public class Role {

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
        System.out.println(this.role+role+"+++++++++++++++++");
    }
}
