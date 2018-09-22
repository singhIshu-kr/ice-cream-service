package iceCreamService.response;

import iceCreamService.request.MemberInfo;

import java.util.List;

public class TeamInfo {
    public final String name;
    public final List<MemberInfo> memberInfo;

    public TeamInfo(String name, List<MemberInfo> memberInfos) {
        this.name = name;
        this.memberInfo = memberInfos;
    }
}
