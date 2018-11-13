package iceCreamService.service;


import iceCreamService.exception.ResourceNotFoundException;
import iceCreamService.model.Team;
import iceCreamService.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class  TeamService {
    private TeamRepository teamRepository;

    @Autowired
    TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public void addTeam(String teamName) {
        Team team = new Team(teamName);
        teamRepository.save(team);
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team getTeam(String teamId) throws ResourceNotFoundException {
        Optional<Team> byId = teamRepository.findById(teamId);
        if(byId.isPresent()){
            return byId.get();
        }
        throw new ResourceNotFoundException("Team with this name doesn't exist");
    }
}
