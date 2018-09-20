package iceCreamService.service;


import iceCreamService.model.Team;
import iceCreamService.exception.TeamNotFoundException;
import iceCreamService.repository.TeamRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


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
        Team magneto = new Team("Magneto", "magneto@gmail.com","abcd");
        teamService.addTeam(magneto);
        verify(teamRepository,times(1)).save(magneto);
    }

    @Test
    public void shouldDeleteTeam() {
        String email = "magneto@gmail.com";
        teamService.deleteTeam(email);
        verify(teamRepository,times(1)).deleteByEmail(email);
    }

    @Test(expected = TeamNotFoundException.class)
    public void shouldThrowExceptionIfEmailIdNotFound() throws TeamNotFoundException {
        String email = "abcd@gmail.com";
        when(teamRepository.findByEmail(email)).thenReturn(Optional.empty());
        teamService.getTeamByEmail(email);
    }

    @Test
    public void shouldGetTheTeamInfoIfEmailIdIsValid() throws TeamNotFoundException {
        String email = "abcd@gmail.com";
        Team magneto = new Team("Magneto", "magneto@gmail.com","abcd");
        when(teamRepository.findByEmail(email)).thenReturn(Optional.of(magneto));
        teamService.getTeamByEmail(email);
        verify(teamRepository,times(1)).findByEmail(email);
    }

    @Test
    public void shouldGetAllTheTeams() {
        teamService.findAllTeams();
        verify(teamRepository,times(1)).findAll();
    }
}
