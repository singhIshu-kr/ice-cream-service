package iceCreamService;

import iceCreamService.model.Team;
import iceCreamService.repository.MemberRepository;
import iceCreamService.repository.TeamRepository;
import iceCreamService.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner{

    private TeamService teamService;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MemberRepository memberRepository;


    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

    @Override
    public void run(String... args) {
        memberRepository.deleteAll();
        teamService = new TeamService(teamRepository);
        teamService.addTeam("Hero","honda@gmail.com","honda");
        teamService.addTeam("Magneto","magneto@gmail.com","abcd");
        for (Team team : teamRepository.findAll()) {
            System.out.println(team);
        }
    }
}
