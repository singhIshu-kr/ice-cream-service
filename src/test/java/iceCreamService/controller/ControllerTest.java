package iceCreamService.controller;

import iceCreamService.exception.MemberWithIdExistsException;
import iceCreamService.model.Member;
import iceCreamService.model.User;
import iceCreamService.exception.TeamNotFoundException;
import iceCreamService.request.NewMemberRequest;
import iceCreamService.request.NewTeamRequest;
import iceCreamService.request.NewUserRequest;
import iceCreamService.service.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ControllerTest {

    @Mock
    private MemberService memberService;

    @Mock
    private UserService userService;

    @Mock
    private ScoreService scoreService;

    @Mock
    private TeamService teamService;

    @Mock
    private SessionService sessionService;

    @Mock
    private RoleTrackerService roleTrackerService;

    public Controller controller;

    @Before
    public void setUp() {
        controller = new Controller(sessionService, memberService, scoreService, userService,teamService,roleTrackerService);
    }

    @Test
    public void addNewUser() {
        NewUserRequest newUser = new NewUserRequest();
        newUser.name = "Magneto";
        newUser.email = "abcd";
        newUser.password = "abcd";
        User user = new User("Magneto", "abcd","abcd");
        controller.addNewUser(newUser);
        verify(userService,times(1)).addUser(user.name,user.email,user.password);
    }

    @Test
    public void getListOfMembers() {
        controller.getListOfMembers();
        verify(memberService,times(1)).findAllMembers();
    }

    @Test
    public void addMemberToTeam() throws MemberWithIdExistsException {
        NewMemberRequest addMember = new NewMemberRequest();
        addMember.name = "Ishu";
        addMember.teamId = "234";
        Member member = new Member("Ishu", "234");
        when(sessionService.isValidSession("abcd","abcd")).thenReturn(true);
        controller.addMemberToTeam("abcd","abcd",addMember);
        verify(memberService,times(1)).addMember(member);
    }

    @Test
    public void getAllMembersOfTeam() throws TeamNotFoundException {
        controller.getAllMembersOfTeam("1234");
        verify(memberService,times(1)).getAllMembersOfTeam("1234");
    }

    @Test
    public void shouldAddTeamForUser() {
        NewTeamRequest newTeamRequest = new NewTeamRequest();
        newTeamRequest.userId="Debu";
        newTeamRequest.teamName="Magneto";
        when(sessionService.isValidSession("abcd","1234")).thenReturn(true);
        controller.addNewTeam("1234","abcd",newTeamRequest);
        verify(teamService,times(1)).addTeam("Magneto");
        verify(roleTrackerService,times(1)).addRoleTrack("Magneto","Debu");
    }

    @Test
    public void shouldReturnAllTheTeamsOfUser(){
        when(sessionService.isValidSession("abcd","1234")).thenReturn(true);
        controller.getAllTeamsOfUser("1234","abcd","Debu");
        verify(roleTrackerService,times(1)).getAllTeamsOfUser("Debu");
    }
}