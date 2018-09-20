package iceCreamService.service;


import iceCreamService.model.Team;
import iceCreamService.exception.TeamNotFoundException;
import iceCreamService.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {
    private TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public void addTeam(Team team) {
        System.out.println(team.toString()  );
        teamRepository.save(team);
    }

    public void deleteTeam(String email) {
        teamRepository.deleteByEmail(email);
    }

    public Team getTeamByEmail(String email) throws TeamNotFoundException {
        Optional<Team> team = teamRepository.findByEmail(email);
        if(team.isPresent()){
            return team.get();
        }
        else {
            throw new TeamNotFoundException("Team with this email is not present");
        }
    }

    public List<Team> findAllTeams() {
        return teamRepository.findAll();
    }

    public boolean isValidEmailAndPassword(String email, String password) {
        System.out.println(teamRepository.existsById(email) + " "+teamRepository.existsByPassword(password));
        return teamRepository.existsById(email) && teamRepository.existsByPassword(password);
    }

    public String getName(String teamID) {
        return teamRepository.findById(teamID).get().name;
    }
}
