package iceCreamService.service;

import iceCreamService.model.RoleTrack;
import iceCreamService.model.Team;
import iceCreamService.repository.RoleTrackerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RoleTrackerServiceTest {

    @Mock
    private RoleTrackerRepository roleTrackerRepository;

    private RoleTrackerService roleTrackerService;


    @Before
    public void setUp() throws Exception {
        roleTrackerService = new RoleTrackerService(roleTrackerRepository);
    }

    @Test
    public void shouldAddTeamIfTheTeamNameIsUnique(){
        RoleTrack roleTrack = new RoleTrack("1234", "Magneto","ADMIN");
        roleTrackerService.addRoleTrack("Magneto","1234");
        verify(roleTrackerRepository,times(1)).save(roleTrack);
    }
}
