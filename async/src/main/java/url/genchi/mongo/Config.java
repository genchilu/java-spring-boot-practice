package url.genchi.mongo;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by mac on 2016/6/3.
 */
@Configuration
@EnableAutoConfiguration
@EnableMongoRepositories
public class Config extends AbstractMongoConfiguration{
    @Value("${mongo.ip}")
    private String mongoIp;
    @Value("${mongo.port}")
    private int port;
    @Value("${mongo.user}")
    private String user;
    @Value("${mongo.passwd}")
    private String passwd;
    @Value("${mongo.db}")
    private String db;

    protected String getDatabaseName() {
        return db;
    }
    public Mongo mongo() throws Exception {
        return new MongoClient(new MongoClientURI( "mongodb://" + user + ":" + passwd + "@" + mongoIp));
    }

    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), db);
    }
}
