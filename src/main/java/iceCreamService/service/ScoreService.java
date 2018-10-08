package iceCreamService.service;

import iceCreamService.model.Score;
import iceCreamService.exception.InvalidMemberOrTeamIdException;
import iceCreamService.exception.NoScoreToBeReducedException;
import iceCreamService.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScoreService {
    private ScoreRepository scoreRepository;
    private MemberService memberService;

    @Autowired
    public ScoreService(ScoreRepository scoreRepository, MemberService memberService) {
        this.scoreRepository = scoreRepository;
        this.memberService = memberService;
    }

    public void addScore(Score score) throws InvalidMemberOrTeamIdException {
        if(memberService.isTeamIDAndMemberIdMatch(score.memberId,score.teamId)){
            scoreRepository.save(score);
        }
        else {
            throw new InvalidMemberOrTeamIdException("Member Id and User Id don't match or Don't Exist");
        }
    }

    public long getScore(String memberId) {
        List<Score> memberEntries = scoreRepository.findAllByMemberId(memberId);
        long score = memberEntries.stream().filter((memberEntry) -> !memberEntry.isReedemed).count();
        return score;
    }

    public void resetScore(String memberId) {
        List<Score> memberEntries = scoreRepository.findAllByMemberId(memberId);
        memberEntries.forEach((memberEntry)-> {
            memberEntry.setRedeemed();
            scoreRepository.save(memberEntry);
        });
    }

    public List<Score> getNonReedemedEntries(String memberId){
        List<Score> memberEntries = scoreRepository.findAllByMemberId(memberId);
        return memberEntries.stream().filter((memberEntry -> !memberEntry.isReedemed)).collect(Collectors.toList());
    }

    public void reduceScore(String memberId, String teamId) throws NoScoreToBeReducedException {
        List<Score> nonReedemedEntries = getNonReedemedEntries(memberId);
        if(!nonReedemedEntries.isEmpty()){
            Score score = nonReedemedEntries.get(0);
            score.setRedeemed();
            scoreRepository.save(score);
        }else{
            throw new NoScoreToBeReducedException("The member has no score to be reduced");
        }
    }

    public List<Score> findAllTeams() {
        return scoreRepository.findAll();
    }
}
