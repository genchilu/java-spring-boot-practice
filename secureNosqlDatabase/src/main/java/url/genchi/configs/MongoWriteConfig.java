package url.genchi.configs;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.web.PageableDefault;

/**
 * Created by genchilu on 2016/5/3.
 */
@Configuration
@EnableAutoConfiguration
@EnableMongoRepositories(basePackages = "url.genchi.repository.write", mongoTemplateRef = "write")
public class MongoWriteConfig extends AbstractMongoConfiguration {
    @Value("${mongo.write.ip}")
    private String mongoIp;
    @Value("${mongo.write.port}")
    private int port;
    @Value("${mongo.write.user}")
    private String user;
    @Value("${mongo.write.passwd}")
    private String passwd;
    @Value("${mongo.write.db}")
    private String db;

    protected String getDatabaseName() {
        return db;
    }
    public Mongo mongo() throws Exception {
        System.out.println("get write db");
        return new MongoClient(new MongoClientURI( "mongodb://" + user + ":" + passwd + "@" + mongoIp));
    }
    @Bean(name = "write")
    public MongoTemplate mongoTemplate() throws Exception {
        System.out.println("get write template");
        return new MongoTemplate(mongo(), db);
    }

}
