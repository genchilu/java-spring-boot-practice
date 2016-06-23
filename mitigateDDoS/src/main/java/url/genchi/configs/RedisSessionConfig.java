package url.genchi.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
/**
 * Created by mac on 2016/6/22.
 */
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60)
public class RedisSessionConfig {
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}