package iceCreamService.repository;

import iceCreamService.model.Score;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreRepository extends MongoRepository<Score,String> {
    List<Score> findAllByMemberId(String memberId);
    List<Score> findAllByisReedemed(String memberId);
}
