package iceCreamService.model;


import org.springframework.data.annotation.Id;

import java.util.Date;

public class Score {

    @Id
    public String id;
    public final String memberId;
    public final String teamId;
    public final Date date;
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

    @Override
    public String toString() {
        return "Score{" +
                "id='" + id + '\'' +
                ", memberId='" + memberId + '\'' +
                ", teamId='" + teamId + '\'' +
                ", date=" + date +
                ", isReedemed=" + isReedemed +
                '}';
    }
}
