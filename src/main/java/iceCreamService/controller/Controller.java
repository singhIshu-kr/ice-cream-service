package iceCreamService.controller;


import iceCreamService.exception.MemberWithIdExistsException;
import iceCreamService.model.Member;
import iceCreamService.model.Score;
import iceCreamService.model.User;
import iceCreamService.exception.InvalidMemberOrTeamIdException;
import iceCreamService.exception.NoScoreToBeReducedException;
import iceCreamService.exception.TeamNotFoundException;
import iceCreamService.request.*;
import iceCreamService.response.TeamInfo;
import iceCreamService.service.MemberService;
import iceCreamService.service.ScoreService;
import iceCreamService.service.SessionService;
import iceCreamService.service.UserService;
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

    @Autowired
    public Controller(SessionService sessionService, MemberService memberService, ScoreService scoreService, UserService userService) {
        this.sessionService = sessionService;
        this.memberService = memberService;
        this.scoreService = scoreService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String getHello() {
        return "Hiie";
    }

    @CrossOrigin
    @PostMapping("/addUser")
    public ResponseEntity addNewTeam(@RequestBody NewTeamRequest addTeam) {
        userService.addUser(addTeam.name,addTeam.email,addTeam.password);
        String token = UUID.randomUUID().toString() + ":" + System.currentTimeMillis();
        sessionService.addSession(token, addTeam.email);
        return new ResponseEntity(token, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/getTeam/{email}")
    public User getListOfMembers(@PathVariable String email) throws TeamNotFoundException {
        return userService.getTeamByEmail(email);
    }

    @CrossOrigin
    @GetMapping("/allTeam")
    public List<User> getListOfTeam() {
        return userService.findAllTeams();
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
        if (sessionService.isValidSession(accessToken, email)) {
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
    @PostMapping("/loginTeam")
    public ResponseEntity loginTeam(@RequestBody LoginTeam loginTeam) {
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
    @PostMapping("/signOutTeam")
    public ResponseEntity signOutTeam(
            @RequestHeader(value = "email") String email,
            @RequestHeader(value = "accessToken") String accessToken) {
        if (sessionService.isValidSession(accessToken, email)) {
            sessionService.removeSession(accessToken);
            System.out.println(email + accessToken + "signout");
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
        System.out.println("lof33333333");
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
}
