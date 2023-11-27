package blank.translator;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranslationRepository extends MongoRepository<Translation, String> {
    List<Translation> findBySessionID(String sessionID);
}
