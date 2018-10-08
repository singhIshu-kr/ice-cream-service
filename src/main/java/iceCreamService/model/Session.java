package iceCreamService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Session")
public class Session {

    @Id
    public String accesstoken;
    public String emailId;

    public Session(String accesstoken,String emailId) {
        this.accesstoken = accesstoken;
        this.emailId = emailId;
    }

    @Override
    public String toString() {
        return "Session{" +
                "accesstoken='" + accesstoken + '\'' +
                ", emailId='" + emailId + '\'' +
                '}';
    }
}
