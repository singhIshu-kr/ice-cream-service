package iceCreamService.service;


import iceCreamService.exception.InvalidRequestAccess;
import iceCreamService.exception.NoRoleForUserAndTeam;
import iceCreamService.model.Role;
import iceCreamService.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleManagerService {

    private RoleRepository roleRepository;

    @Autowired
    RoleManagerService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    public void addRoleTrack(String teamName, String userId, String role) {
        if(!isRoleAvailableFor(userId,teamName)){
            Role roleRecord = new Role(userId, teamName, role);
            roleRepository.save(roleRecord);
        }
    }

    private boolean isRoleAvailableFor(String userId, String teamName) {
        return roleRepository.findByTeamId(teamName).stream()
                .anyMatch((roleTrack -> roleTrack.userId.equals(userId)));
    }

    public List<Role> getAllTeamsOfUser(String userId) {
        List<Role> byUserId = roleRepository.findByUserId(userId);
        return byUserId.stream().filter(roleTrack -> roleTrack.role.equals("ADMIN")).collect(Collectors.toList());
    }

    public List<Role> getAllEntries() {
        return roleRepository.findAll();
    }

    public boolean isTeamOfSameUser(String email, String teamID) {
        List<Role> allTeamsOfUser = getAllTeamsOfUser(email);
        return allTeamsOfUser.stream().anyMatch(roleTrack -> roleTrack.teamId.equals(teamID));
    }

    public void requestAccessOfTeam(String userId, String teamName) throws InvalidRequestAccess {
        List<Role> byTeamId = roleRepository.findByTeamId(teamName);
        if(!byTeamId.isEmpty() && !isTeamOfSameUser(userId,teamName)){
            addRoleTrack(teamName, userId, "WAITING");
        }
        else{
            throw new InvalidRequestAccess("The request for team access is invalid");
        }
    }

    public Role getRoleInfoOf(String userId, String teamName) throws NoRoleForUserAndTeam {
        List<Role> byTeamId = roleRepository.findByTeamId(teamName);
        List<Role> roleInfo = byTeamId.stream().filter(role -> role.userId.equals(userId)).collect(Collectors.toList());
        if(roleInfo.isEmpty()){
            throw new NoRoleForUserAndTeam("No role present for this user and team");
        }
        System.out.println(roleInfo.get(0));
        return roleInfo.get(0);
    }


    public void updateRole(String userId, String teamName, String role) throws NoRoleForUserAndTeam {
        Role roleInfo = getRoleInfoOf(userId, teamName);
        roleInfo.setRole(role);
        roleRepository.save(roleInfo);
    }

    public List<Role> getGuestRoleOfTeam(String teamName){
        return roleRepository.findByTeamId(teamName).stream().filter(role -> role.role.equals("GUEST")).collect(Collectors.toList());
    }

    public List<Role> getWaitingRoleOfTeam(String teamName){
        return roleRepository.findByTeamId(teamName).stream().filter(role -> role.role.equals("WAITING")).collect(Collectors.toList());
    }

    public List<Role> getAllAccessRequests(String userId) {
        List<Role> allTeamsOfUser = getAllTeamsOfUser(userId);
        List<List<Role>> accessRequestOfTeams = allTeamsOfUser.stream().map(role -> getWaitingRoleOfTeam(role.teamId)).collect(Collectors.toList());
        return accessRequestOfTeams.stream().flatMap(List::stream).collect(Collectors.toList());
    }
}
