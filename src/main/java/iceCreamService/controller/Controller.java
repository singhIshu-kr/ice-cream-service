package iceCreamService.controller;


import iceCreamService.exception.*;
import iceCreamService.model.*;
import iceCreamService.request.*;
import iceCreamService.response.RelatedTeams;
import iceCreamService.response.TeamInfo;
import iceCreamService.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
public class Controller {

    private SessionService sessionService;
    private MemberService memberService;
    private ScoreService scoreService;
    private UserService userService;
    private TeamService teamService;
    private RoleManagerService roleManagerService;

    @Autowired
    Controller(SessionService sessionService, MemberService memberService, ScoreService scoreService, UserService userService, TeamService teamService, RoleManagerService roleManagerService) {
        this.sessionService = sessionService;
        this.memberService = memberService;
        this.scoreService = scoreService;
        this.userService = userService;
        this.teamService = teamService;
        this.roleManagerService = roleManagerService;
    }

    @GetMapping("/allUsers")
    public List<User> getAllTheUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/allRoles")
    public List<Role> getAllTheTeamWithRoles() {
        return roleManagerService.getAllEntries();
    }

    @GetMapping("/")
    public String getHello() {
        return "Hiie";
    }

    @CrossOrigin
    @PostMapping("/addUser")
    ResponseEntity addNewUser(@RequestBody NewUserRequest newUserRequest) {
        userService.addUser(newUserRequest.name, newUserRequest.email, newUserRequest.password);
        String token = UUID.randomUUID().toString() + ":" + System.currentTimeMillis();
        sessionService.addSession(token, newUserRequest.email);
        return new ResponseEntity(token, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/getTeam/{email}")
    public User getListOfMembers(@PathVariable String email) throws TeamNotFoundException {
        return userService.getTeamByEmail(email);
    }

    @GetMapping("/allMember")
    public List<Member> getListOfMembers() {
        return memberService.findAllMembers();
    }

    @CrossOrigin
    @PostMapping("/addMember")
    public ResponseEntity addMemberToTeam(
            @RequestHeader(value = "email") String email,
            @RequestHeader(value = "accessToken") String accessToken,
            @RequestBody NewMemberRequest addMember) throws MemberWithIdExistsException {
        if (sessionService.isValidSession(accessToken, email)) {
            Member member = new Member(addMember.name, addMember.teamId);
            memberService.addMember(member);
            String token = UUID.randomUUID().toString() + ":" + System.currentTimeMillis();
            sessionService.addSession(token, addMember.teamId);
            return new ResponseEntity(token, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/team/{teamID}")
    public List<Member> getAllMembersOfTeam(@PathVariable String teamID) {
        return memberService.getAllMembersOfTeam(teamID);
    }

    @CrossOrigin
    @PostMapping("/addScore")
    public ResponseEntity addScoreOfMember(
            @RequestHeader(value = "email") String email,
            @RequestHeader(value = "accessToken") String accessToken,
            @RequestBody TeamAndMemberInfo info) throws InvalidMemberOrTeamIdException {
        if (sessionService.isValidSession(accessToken, email)) {
            Score score = new Score(info.id, info.teamId, new Date(), false);
            scoreService.addScore(score);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @CrossOrigin
    @PostMapping("/reduceScore")
    public ResponseEntity reduceScoreOfMember(
            @RequestHeader(value = "email") String email,
            @RequestHeader(value = "accessToken") String accessToken,
            @RequestBody TeamAndMemberInfo info) throws NoScoreToBeReducedException {
        if (sessionService.isValidSession(accessToken, email)) {
            scoreService.reduceScore(info.id, info.teamId);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @CrossOrigin
    @GetMapping("/read/{teamID}")
    public ResponseEntity getMemberInfoOfTeam(
            @RequestHeader(value = "email") String email,
            @RequestHeader(value = "accessToken") String accessToken,
            @PathVariable String teamID) {
        if (sessionService.isValidSession(accessToken, email) && roleManagerService.isTeamOfSameUser(email, teamID)) {
            List<Member> allMembersOfTeam = memberService.getAllMembersOfTeam(teamID);
            ArrayList<MemberInfo> memberInfos = new ArrayList<>();
            allMembersOfTeam.forEach((member -> {
                long score = scoreService.getScore(member.id);
                MemberInfo memberInfo = new MemberInfo(member.name, member.id, member.teamID, score);
                memberInfos.add(memberInfo);
            }));
            String teamName = userService.getName(email);
            TeamInfo teamInfo = new TeamInfo(teamName, memberInfos);
            return new ResponseEntity(teamInfo, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @CrossOrigin
    @GetMapping("/getMember/{teamID}")
    public List<Member> getMemberOfTeam(@PathVariable String teamID, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        return memberService.getAllMembersOfTeam(teamID);
    }

    @CrossOrigin
    @GetMapping("/allScore")
    public List<Score> getScoreTable() {
        return scoreService.findAllTeams();
    }

    @CrossOrigin
    @DeleteMapping("/{teamId}/removeAll")
    public String removeAll(@PathVariable String teamId) {
        memberService.removeAll();
        return "Deleted";
    }

    @CrossOrigin
    @PostMapping("/remove")
    public ResponseEntity removeMember(
            @RequestHeader(value = "email") String email,
            @RequestHeader(value = "accessToken") String accessToken,
            @RequestBody MemberId memberId) {
        if (sessionService.isValidSession(accessToken, email)) {
            memberService.removeMember(memberId.id);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @CrossOrigin
    @PostMapping("/reset-score")
    public ResponseEntity resetScore(
            @RequestHeader(value = "email") String email,
            @RequestHeader(value = "accessToken") String accessToken,
            @RequestBody MemberId memberId) {
        if (sessionService.isValidSession(accessToken, email)) {
            scoreService.resetScore(memberId.id);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @CrossOrigin
    @PostMapping("/loginUser")
    public ResponseEntity loginUser(@RequestBody LoginTeam loginTeam) {
        System.out.println(loginTeam.email + loginTeam.password);
        if (userService.isValidEmailAndPassword(loginTeam.email, loginTeam.password)) {
            String token = UUID.randomUUID().toString() + ":" + System.currentTimeMillis();
            sessionService.addSession(token, loginTeam.email);
            return new ResponseEntity(token, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
        }
    }

    @CrossOrigin
    @PostMapping("/signOutUser")
    public ResponseEntity signOutUser(
            @RequestHeader(value = "email") String email,
            @RequestHeader(value = "accessToken") String accessToken) {
        if (sessionService.isValidSession(accessToken, email)) {
            sessionService.removeSession(accessToken);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin
    @GetMapping("/isLoggedIn")
    public ResponseEntity isLoggedIn(
            @RequestHeader(value = "email") String email,
            @RequestHeader(value = "accessToken") String accessToken) {
        if (sessionService.isValidSession(accessToken, email)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @CrossOrigin
    @PostMapping("/newTeam")
    public ResponseEntity addNewTeam(
            @RequestHeader(value = "email") String email,
            @RequestHeader(value = "accessToken") String accessToken,
            @RequestBody NewTeamRequest newTeamRequest) {
        if (sessionService.isValidSession(accessToken, email)) {
            teamService.addTeam(newTeamRequest.teamName);
            roleManagerService.addRoleTrack(newTeamRequest.teamName, newTeamRequest.userId, "ADMIN");
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @CrossOrigin
    @GetMapping("/myTeams/{userId}")
    public ResponseEntity getAllTeamsOfUser(
            @RequestHeader(value = "email") String email,
            @RequestHeader(value = "accessToken") String accessToken,
            @PathVariable String userId) {
        if (sessionService.isValidSession(accessToken, email)) {
            List<Role> allTeamsOfUser = roleManagerService.getAllTeamsOfUser(email);
            RelatedTeams relatedTeams = new RelatedTeams(allTeamsOfUser);
            return new ResponseEntity(relatedTeams, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/allTeams")
    public List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping("/allMembers")
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    @CrossOrigin
    @GetMapping("/search/{teamId}")
    public ResponseEntity searchTeam(
            @RequestHeader(value = "email") String email,
            @RequestHeader(value = "accessToken") String accessToken,
            @PathVariable String teamId) throws ResourceNotFoundException {
        if (sessionService.isValidSession(accessToken, email)) {
            Team team = teamService.getTeam(teamId);
            return new ResponseEntity(team, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @CrossOrigin
    @PostMapping("/request")
    public ResponseEntity requestTeamAccess(
            @RequestHeader(value = "email") String email,
            @RequestHeader(value = "accessToken") String accessToken,
            @RequestBody RequestAccess requestAccess) throws InvalidRequestAccess {
        if (sessionService.isValidSession(accessToken, email)) {
            roleManagerService.requestAccessOfTeam(requestAccess.userId, requestAccess.teamName);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/permitAccess")
    public ResponseEntity alotRole(
//            @RequestHeader(value = "email") String email,
//            @RequestHeader(value = "accessToken") String accessToken,
            @RequestBody RoleUpdate roleUpdate) throws NoRoleForUserAndTeam {
//        if (sessionService.isValidSession(accessToken, email)) {
            roleManagerService.updateRole(roleUpdate.userId, roleUpdate.teamName, roleUpdate.role);
            return new ResponseEntity(HttpStatus.OK);
//        }
//        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @CrossOrigin
    @GetMapping("/allRequests/{userId}")
    public ResponseEntity getAllAccessRequests(
            @RequestHeader(value = "email") String email,
            @RequestHeader(value = "accessToken") String accessToken,
            @PathVariable String userId){
        if (sessionService.isValidSession(accessToken, email)) {
            List<Role> allAccessRequests = roleManagerService.getAllAccessRequests(userId);
            return new ResponseEntity(allAccessRequests, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
}