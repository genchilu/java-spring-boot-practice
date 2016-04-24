package url.genchi.jdbc.configs;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class jdbcConfigs {
    @Primary
    @Bean(name = "readDataSource")
    @ConfigurationProperties(prefix = "datasource.read")
    public DataSource readDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "readJdbc")
    public JdbcTemplate readJdbcTemplate(@Qualifier("readDataSource") DataSource readDataSource) {
       return new JdbcTemplate(readDataSource);
    }

    @Bean(name = "writeDataSource")
    @ConfigurationProperties(prefix = "datasource.write")
    public DataSource writeDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "writeJdbc")
    public JdbcTemplate writeJdbcTemplate(@Qualifier("writeDataSource") DataSource writeDataSource) {
       return new JdbcTemplate(writeDataSource);
    }
}
