package iceCreamService.service;


import iceCreamService.model.User;
import iceCreamService.exception.TeamNotFoundException;
import iceCreamService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.mindrot.jbcrypt.BCrypt;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(String name, String email, String password) {
        String encryptedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(name, email, encryptedPassword);
        userRepository.save(user);
    }

    void deleteTeam(String email) {
        userRepository.deleteByEmail(email);
    }

    public User getTeamByEmail(String email) throws TeamNotFoundException {
        Optional<User> team = userRepository.findByEmail(email);
        if(team.isPresent()){
            return team.get();
        }
        else {
            throw new TeamNotFoundException("User with this email is not present");
        }
    }

    List<User> findAllTeams() {
        return userRepository.findAll();
    }

    public boolean isValidEmailAndPassword(String email, String password) {
        Optional<User> team = userRepository.findByEmail(email);
        return team.filter(user -> BCrypt.checkpw(password, user.password)).isPresent();
    }

    public String getName(String teamID) {
        return userRepository.findById(teamID).get().name;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
