package iceCreamService.service;


import iceCreamService.model.RoleTrack;
import iceCreamService.repository.RoleTrackerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleTrackerService {

    private RoleTrackerRepository roleTrackerRepository;

    @Autowired
    public RoleTrackerService(RoleTrackerRepository roleTrackerRepository) {
        this.roleTrackerRepository = roleTrackerRepository;
    }

    public void addRoleTrack(String teamName, String userId) {
        RoleTrack roleRecord = new RoleTrack(userId, teamName, "ADMIN");
        roleTrackerRepository.save(roleRecord);
    }

    public List<RoleTrack> getAllTeamsOfUser(String userId) {
        return roleTrackerRepository.findByUserId(userId);
    }
}
