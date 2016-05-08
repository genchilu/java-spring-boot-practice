package url.genchi.configs;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by genchilu on 2016/5/4.
 */
@Configuration
@EnableAutoConfiguration
@EnableMongoRepositories(basePackages = "url.genchi.repository.read", mongoTemplateRef = "read")
public class MongoReadConfig extends AbstractMongoConfiguration {
    @Value("${mongo.read.ip}")
    private String mongoIp;
    @Value("${mongo.read.port}")
    private int port;
    @Value("${mongo.read.user}")
    private String user;
    @Value("${mongo.read.passwd}")
    private String passwd;
    @Value("${mongo.read.db}")
    private String db;

    protected String getDatabaseName() {
        return db;
    }
    public Mongo mongo() throws Exception {
        return new MongoClient(new MongoClientURI( "mongodb://" + user + ":" + passwd + "@" + mongoIp));
    }
    @Bean(name = "read")
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), db);
    }
}
