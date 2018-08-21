package iceCreamService.Service;

import iceCreamService.Domain.Member;
import iceCreamService.Exception.MemberNotFoundException;
import iceCreamService.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class MemberService {
    private MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public boolean isValidMemberId(String id) {
        return memberRepository.existsById(id);
    }

    public boolean isTeamIDAndMemberIdMatch(String memberId, String teamId) {
        Optional<Member> member = memberRepository.findById(memberId);
        if(member.isPresent() && member.get().teamID == teamId ){
            return true;
        }else {
            return false;
        }
    }

    public void addMember(Member member) {
        memberRepository.save(member);
    }

    public void alotTeam(String memberId, String teamId) throws MemberNotFoundException {
        Optional<Member> memberToBeAssigned = memberRepository.findById(memberId);
        if(memberToBeAssigned.isPresent()){
            Member member = memberToBeAssigned.get();
            member.setTeamId(teamId);
            memberRepository.save(member);
        }
        else {
            throw new MemberNotFoundException("Member with this id does'nt exists");
        }
    }
}
