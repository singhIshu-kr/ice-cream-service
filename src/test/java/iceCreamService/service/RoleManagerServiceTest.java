package iceCreamService.service;

import iceCreamService.model.Role;
import iceCreamService.repository.RoleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RoleManagerServiceTest {

    @Mock
    private RoleRepository roleRepository;

    private RoleManagerService roleManagerService;


    @Before
    public void setUp() throws Exception {
        roleManagerService = new RoleManagerService(roleRepository);
    }

    @Test
    public void shouldAddTeamIfTheTeamNameIsUnique(){
        Role role = new Role("1234", "Magneto","ADMIN");
        roleManagerService.addRoleTrack("Magneto","1234", "ADMIN");
        verify(roleRepository,times(1)).save(role);
    }


}
