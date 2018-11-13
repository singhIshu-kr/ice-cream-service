package iceCreamService.service;

import iceCreamService.exception.MemberWithIdExistsException;
import iceCreamService.model.Member;
import iceCreamService.exception.MemberNotFoundException;
import iceCreamService.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private MemberRepository memberRepository;

    @Autowired
    MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    boolean isValidMemberId(String id) {
        return memberRepository.existsById(id);
    }

    boolean isTeamIDAndMemberIdMatch(String memberId, String teamId) {
        Optional<Member> member = memberRepository.findById(memberId);
        System.out.println(member.get().teamID.equals(teamId)+"I am Member");
        return member.get().teamID.equals(teamId);
    }

    public void addMember(Member member) throws MemberWithIdExistsException {
        if(!isMemberWithSameName(member.teamID,member.name)){
            memberRepository.save(member);
            return;
        }
        throw new MemberWithIdExistsException("Member with this name exists");
    }

    private boolean isMemberWithSameName(String teamId, String name){
        List<Member> teamMembers = memberRepository.findByTeamID(teamId);
        System.out.println(teamMembers.stream().anyMatch((member -> member.name.equals(name))));
        return teamMembers.stream().anyMatch((member -> member.name.equals(name)));
    }

    void alotTeam(String memberId, String teamId) throws MemberNotFoundException {
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

    public List<Member> getAllMembersOfTeam(String teamID) {
        return memberRepository.findByTeamID(teamID);
    }

    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    public void removeAll() {
        memberRepository.deleteAll();
    }

    public void removeMember(String memberId) {
        memberRepository.deleteById(memberId);
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
}
