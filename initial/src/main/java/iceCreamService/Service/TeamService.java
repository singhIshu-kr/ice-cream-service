package iceCreamService.Service;


import iceCreamService.Domain.Team;
import iceCreamService.Repository.TeamRepository;

public class TeamService {
    private TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public void addTeam(Team team) {
        teamRepository.save(team);
    }

    public void deleteTeam(String email) {
        teamRepository.deleteByEmail(email);
    }
}
