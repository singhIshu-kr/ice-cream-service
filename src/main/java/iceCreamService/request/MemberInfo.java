package iceCreamService.request;

public class MemberInfo {
    public final String name;
    public final String id;
    public final String teamID;
    public final long score;

    public MemberInfo(String name, String id, String teamID, long score) {
        this.name = name;
        this.id = id;
        this.teamID = teamID;
        this.score = score;
    }
}
