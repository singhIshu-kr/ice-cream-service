package iceCreamService.repository;

import iceCreamService.model.Team;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TeamRepository extends MongoRepository<Team,String> {
    void deleteByEmail(String email);

    Optional<Team> findByEmail(String email);

    boolean existsByPassword(String password);
}
