package iceCreamService.repository;

import iceCreamService.model.Member;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MemberRepository extends MongoRepository<Member,String> {
    List<Member> findByTeamID(String teamID);
}

