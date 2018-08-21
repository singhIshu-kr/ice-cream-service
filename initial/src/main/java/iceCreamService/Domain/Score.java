package iceCreamService.Domain;


import org.springframework.data.annotation.Id;

import java.util.Date;

public class Score {

    @Id
    private String id;
    public final String memberId;
    public final String teamId;
    private final Date date;
    public Boolean isReedemed;

    public Score(String memberId, String teamId, Date date, Boolean isReedemed) {
        this.memberId = memberId;
        this.teamId = teamId;
        this.date = date;
        this.isReedemed = isReedemed;
    }

    public void setRedeemed() {
        this.isReedemed = true;
    }
}
