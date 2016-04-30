package url.genchi.sql.hibernate.configs;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration
@EntityScan(basePackages = "url.genchi.hibernate.entities")
@EnableJpaRepositories(transactionManagerRef = "writeTransactionManager", entityManagerFactoryRef = "writeManagerFactory", basePackages = "url.genchi.hibernate.daos.write.")

public class WriteEntityManager {
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "datasource.write")
    public DataSource writeDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "writeManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean writeManagerFactory(final EntityManagerFactoryBuilder builder) {
        return builder.dataSource(writeDataSource()).build();
    }

    @Bean(name = "writeTransactionManager")
    @Primary
    @Qualifier(value = "writeManagerFactory")
    public JpaTransactionManager writeTransactionManager(final EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
     }

}
