package iceCreamService.Repository;

import iceCreamService.Domain.Score;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ScoreRepository extends MongoRepository<Score,String> {
    List<Score> findAllByMemberId(String memberId);
}
