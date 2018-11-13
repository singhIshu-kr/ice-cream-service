package iceCreamService.repository;

import iceCreamService.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends MongoRepository<Role,String> {

    List<Role> findByUserId(String userId);

    List<Role> findByTeamId(String teamId);
}
