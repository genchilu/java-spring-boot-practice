package url.genchi.repository.write;

        import org.springframework.data.mongodb.repository.MongoRepository;
        import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
        import url.genchi.entities.User;

/**
 * Created by genchilu on 2016/5/3.
 */
public interface MongoWriteRepository extends MongoRepository<User, String> {
        public User findByName(String name);
}
