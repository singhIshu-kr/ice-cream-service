package iceCreamService;

import iceCreamService.Domain.Team;
import iceCreamService.Repository.TeamRepository;
import iceCreamService.Service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner{

    private TeamService teamService;

    @Autowired
    private TeamRepository teamRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        teamService = new TeamService(teamRepository);
        teamRepository.deleteAll();
        teamService.addTeam(new Team("Magneto","magneto@gmail.com"));
        for (Team team : teamRepository.findAll()) {
            System.out.println(team);
        }
    }
}
