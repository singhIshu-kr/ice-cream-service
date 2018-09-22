package iceCreamService.repository;

import iceCreamService.model.Member;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends MongoRepository<Member,String> {
    List<Member> findByTeamID(String teamID);
}

