package url.genchi.repository.read;

import org.springframework.data.mongodb.repository.MongoRepository;
import url.genchi.entities.User;

/**
 * Created by genchilu on 2016/5/4.
 */
public interface MongoReadRepository extends MongoRepository<User, String> {
}
