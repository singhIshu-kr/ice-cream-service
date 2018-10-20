package iceCreamService.service;


import iceCreamService.model.Team;
import iceCreamService.repository.TeamRepository;
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
    public void shouldAddTeamIfTheTeamNameIsUnique(){
        Team magneto = new Team("Magneto");
        teamService.addTeam("Magneto");
        verify(teamRepository,times(1)).save(magneto);
    }
}
