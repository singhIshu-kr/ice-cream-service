package iceCreamService.repository;

import iceCreamService.model.Score;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ScoreRepository extends MongoRepository<Score,String> {
    List<Score> findAllByMemberId(String memberId);
    List<Score> findAllByisReedemed(String memberId);
}
