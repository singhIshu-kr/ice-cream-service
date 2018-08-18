package iceCreamService.Domain;

import org.springframework.data.annotation.Id;

public class Member {

    @Id
    private final String id;
    private final String name;

    public Member(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
