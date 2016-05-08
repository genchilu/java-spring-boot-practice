package url.genchi.repository.read;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import url.genchi.entities.User;

/**
 * Created by genchilu on 2016/5/4.
 */
public interface MongoReadRepository extends MongoRepository<User, String> {
    @Query(value = "{$where: 'this.name == ?0'}", count = true)
    public Long countName(String name);
}
