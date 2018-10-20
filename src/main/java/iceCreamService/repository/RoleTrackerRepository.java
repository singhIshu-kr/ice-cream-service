package iceCreamService.repository;

import iceCreamService.model.RoleTrack;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleTrackerRepository extends MongoRepository<RoleTrack,String> {

    List<RoleTrack> findByUserId(String userId);
}
