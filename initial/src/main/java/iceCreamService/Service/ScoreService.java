package iceCreamService.Service;

import iceCreamService.Domain.Score;
import iceCreamService.Exception.InvalidMemberOrTeamId;
import iceCreamService.Repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ScoreService {
    private ScoreRepository scoreRepository;
    private MemberService memberService;

    @Autowired
    public ScoreService(ScoreRepository scoreRepository, MemberService memberService) {
        this.scoreRepository = scoreRepository;
        this.memberService = memberService;
    }

    public void addScore(Score score) throws InvalidMemberOrTeamId {
        if(memberService.isTeamIDAndMemberIdMatch(score.memberId,score.teamId)){
            scoreRepository.save(score);
        }
        else {
            throw new InvalidMemberOrTeamId("Member Id and Team Id don't match or Don't Exist");
        }
    }

    public long getScore(String memberId) {
        List<Score> memberEntries = scoreRepository.findAllByMemberId(memberId);
        long score = memberEntries.stream().filter((memberEntry) -> memberEntry.isReedemed).count();
        return score;
    }

    public void resetScore(String memberId, String teamId) {
        List<Score> memberEntries = scoreRepository.findAllByMemberId(memberId);
        memberEntries.forEach((memberEntry)->{
            memberEntry.setRedeemed();
        });
    }
}
