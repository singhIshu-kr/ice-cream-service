package iceCreamService.Service;


import iceCreamService.Domain.Team;
import iceCreamService.Repository.TeamRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    private TeamService teamService;

    @Before
    public void setUp() throws Exception {
        teamService = new TeamService(teamRepository);
    }

    @Test
    public void shouldAddTeam() {
        Team magneto = new Team("Magneto", "magneto@gmail.com");
        teamService.addTeam(magneto);
        verify(teamRepository,times(1)).save(magneto);
    }

    @Test
    public void shouldDeleteTeam() {
        String email = "magneto@gmail.com";
        teamService.deleteTeam(email);
        verify(teamRepository,times(1)).deleteByEmail(email);
    }

    @Test
    public void shouldThrowExceptionIfEmailIdNotFound() {
        String invalidEmail = "abcd@gmail.com";
        teamService.deleteTeam(invalidEmail);
    }
}
