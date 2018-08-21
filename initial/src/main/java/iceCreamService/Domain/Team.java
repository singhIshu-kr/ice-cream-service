package iceCreamService.Domain;

import org.springframework.data.annotation.Id;

public class Team {
    @Id
    private String email;
    private String name;

    public Team(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
