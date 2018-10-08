package iceCreamService.controller;

import iceCreamService.exception.MemberWithIdExistsException;
import iceCreamService.model.Member;
import iceCreamService.model.User;
import iceCreamService.exception.TeamNotFoundException;
import iceCreamService.request.NewMemberRequest;
import iceCreamService.request.NewTeamRequest;
import iceCreamService.service.MemberService;
import iceCreamService.service.ScoreService;
import iceCreamService.service.SessionService;
import iceCreamService.service.UserService;
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
    private SessionService sessionService;

    public Controller controller;

    @Before
    public void setUp() {

        controller = new Controller(sessionService, memberService, scoreService, userService);
    }

    @Test
    public void addNewTeam() {
        NewTeamRequest addTeam = new NewTeamRequest();
        addTeam.name = "Magneto";
        addTeam.email = "abcd";
        addTeam.password = "abcd";
        User user = new User("Magneto", "abcd","abcd");
        controller.addNewTeam(addTeam);
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
}