package iceCreamService.controller;

import iceCreamService.model.Member;
import iceCreamService.model.Team;
import iceCreamService.exception.TeamNotFoundException;
import iceCreamService.request.NewMemberRequest;
import iceCreamService.request.NewTeamRequest;
import iceCreamService.service.MemberService;
import iceCreamService.service.ScoreService;
import iceCreamService.service.TeamService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class ControllerTest {

    @Mock
    private MemberService memberService;

    @Mock
    private TeamService teamService;

    @Mock
    private ScoreService scoreService;

    public Controller controller;

    @Before
    public void setUp() {

        controller = new Controller(sessionService, memberService, scoreService, teamService);
    }

    @Test
    public void addNewTeam() {
        NewTeamRequest addTeam = new NewTeamRequest();
        addTeam.name = "Magneto";
        addTeam.email = "abcd";
        addTeam.password = "abcd";
        Team team = new Team("Magneto", "abcd","abcd");
        controller.addNewTeam(addTeam);
        verify(teamService,times(1)).addTeam(team);
    }

    @Test
    public void getListOfMembers() {
        controller.getListOfMembers();
        verify(teamService,times(1)).findAllTeams();
    }

    @Test
    public void addMemberToTeam() {
        NewMemberRequest addMember = new NewMemberRequest();
        addMember.id = "20976";
        addMember.name = "Ishu";
        addMember.teamId = "234";
        Member member = new Member("20976", "Ishu", "234");
        controller.addMemberToTeam(addMember);
        verify(memberService,times(1)).addMember(member);
    }

    @Test
    public void getAllMembersOfTeam() throws TeamNotFoundException {
        controller.getAllMembersOfTeam("1234");
        verify(memberService,times(1)).getAllMembersOfTeam("1234");
    }
}