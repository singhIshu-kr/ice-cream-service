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
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public boolean isValidMemberId(String id) {
        return memberRepository.existsById(id);
    }

    public boolean isTeamIDAndMemberIdMatch(String memberId, String teamId) {
        Optional<Member> member = memberRepository.findById(memberId);
        System.out.println(member.get().teamID.equals(teamId)+"I am Member");
        if(member.isPresent() && (member.get().teamID.equals(teamId))){
            return true;
        }else {
            return false;
        }
    }

    public String addMember(Member member) throws MemberWithIdExistsException {
        if(!isMemberWithSameName(member.teamID,member.name)){
            memberRepository.save(member);
            return "Added";
        }
        throw new MemberWithIdExistsException("Member with this name exists");
    }

    public boolean isMemberWithSameName(String teamId, String name){
        List<Member> teamMembers = memberRepository.findByTeamID(teamId);
        System.out.println(teamMembers.stream().anyMatch((member -> member.name.equals(name))));
        return teamMembers.stream().anyMatch((member -> member.name.equals(name)));
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

    public List<Member> getAllMembersOfTeam(String teamID) {
        return memberRepository.findByTeamID(teamID);
    }

    public List<Member> findAllTeams() {
        return memberRepository.findAll();
    }

    public void removeAll() {
        memberRepository.deleteAll();
    }

    public void removeMember(String memberId) {
        memberRepository.deleteById(memberId);
    }
}
