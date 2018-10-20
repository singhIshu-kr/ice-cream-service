package iceCreamService.service;


import iceCreamService.model.Team;
import iceCreamService.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class  TeamService {
    private TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public void addTeam(String teamName) {
        Team team = new Team(teamName);
        teamRepository.save(team);
    }
}
