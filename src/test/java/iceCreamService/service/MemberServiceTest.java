package iceCreamService.service;


import iceCreamService.exception.MemberWithIdExistsException;
import iceCreamService.model.Member;
import iceCreamService.exception.MemberNotFoundException;
import iceCreamService.repository.MemberRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;
    private MemberService memberService;

    @Before
    public void setUp() {
        memberService = new MemberService(memberRepository);
    }

    @Test
    public void shouldAddMember() throws MemberWithIdExistsException {
        Member member = new Member( "Ishu", "1234");
        memberService.addMember(member);
        verify(memberRepository,times(1)).save(member);
    }

    @Test
    public void shouldAlotMemberATeam() throws MemberNotFoundException {
        Member member = new Member("Ishu", "1234");
        when(memberRepository.findById("20976")).thenReturn(java.util.Optional.ofNullable(member));
        memberService.alotTeam("20976","1234");
        verify(memberRepository,times(1)).findById("20976");
        verify(memberRepository,times(1)).save(member);
    }

    @Test
    public void shouldCallMemberRepository() {
        memberService.isValidMemberId("20976");
        verify(memberRepository,times(1)).existsById("20976");
    }

    @Test
    public void shouldReturnTrueIfMemberBelongsToTheTeam() {
        Member member = new Member( "Ishu", "1234");
        when(memberRepository.findById("20976")).thenReturn(java.util.Optional.ofNullable(member));
        assertEquals(memberService.isTeamIDAndMemberIdMatch("20976","1234"),true);
    }

    @Test
    public void shouldReturnFalseIfMemberDoesNotBelongsToTheTeam() {
        Member member = new Member( "Ishu", "1235");
        when(memberRepository.findById("20976")).thenReturn(java.util.Optional.ofNullable(member));
        assertEquals(memberService.isTeamIDAndMemberIdMatch("20976","1234"),false);
    }

    @Test
    public void shouldReturnAllTheMembersOfParticularTeam() {
        memberService.getAllMembersOfTeam("abcd1234");
        verify(memberRepository,times(1)).findByTeamID("abcd1234");
    }
}

