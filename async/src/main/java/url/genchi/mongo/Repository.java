package url.genchi.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by mac on 2016/6/3.
 */
public interface Repository extends MongoRepository<BankAccount, String> {
    public BankAccount findByName(String name);

}
