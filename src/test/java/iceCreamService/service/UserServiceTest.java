package iceCreamService.service;


import iceCreamService.model.User;
import iceCreamService.exception.TeamNotFoundException;
import iceCreamService.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @Before
    public void setUp() throws Exception {
        userService = new UserService(userRepository);
    }

    @Test
    public void shouldAddTeam() {
        User magneto = new User("Magneto", "magneto@gmail.com","$2a$10$ppU0.sy3Ichg3HQQtxoMGOmRW68lAeJBC1jExHz2w4LctoLbVTNWC");
        userService.addUser(magneto.name,magneto.email,magneto.password);
        verify(userRepository,times(1)).save(any(User.class));
    }

    @Test
    public void shouldDeleteTeam() {
        String email = "magneto@gmail.com";
        userService.deleteTeam(email);
        verify(userRepository,times(1)).deleteByEmail(email);
    }

    @Test(expected = TeamNotFoundException.class)
    public void shouldThrowExceptionIfEmailIdNotFound() throws TeamNotFoundException {
        String email = "abcd@gmail.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        userService.getTeamByEmail(email);
    }

    @Test
    public void shouldGetTheTeamInfoIfEmailIdIsValid() throws TeamNotFoundException {
        String email = "abcd@gmail.com";
        User magneto = new User("Magneto", "magneto@gmail.com","abcd");
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(magneto));
        userService.getTeamByEmail(email);
        verify(userRepository,times(1)).findByEmail(email);
    }

    @Test
    public void shouldGetAllTheTeams() {
        userService.findAllTeams();
        verify(userRepository,times(1)).findAll();
    }
}
