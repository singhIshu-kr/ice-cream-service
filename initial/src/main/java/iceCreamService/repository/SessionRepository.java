package iceCreamService.repository;

import iceCreamService.model.Session;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SessionRepository extends MongoRepository<Session,String> {
}
