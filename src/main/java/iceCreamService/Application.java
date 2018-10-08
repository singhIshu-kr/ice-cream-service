package iceCreamService;

import iceCreamService.model.User;
import iceCreamService.repository.MemberRepository;
import iceCreamService.repository.UserRepository;
import iceCreamService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.stream.Collectors;

@SpringBootApplication
public class Application implements CommandLineRunner{

    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MemberRepository memberRepository;


    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

    @Override
    public void run(String... args) {
        memberRepository.deleteAll();
        userService = new UserService(userRepository);
        userService.addUser("Hero","honda@gmail.com","honda");
        userService.addUser("Magneto","magneto@gmail.com","abcd");
        for (User user : userRepository.findAll()) {
            System.out.println(user);
        }

        System.out.println(Arrays.stream(new String[]{"123","456"}).collect(Collectors.joining())+"demmoooooo");
    }
}
