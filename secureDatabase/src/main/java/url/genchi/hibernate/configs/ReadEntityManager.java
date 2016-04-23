package url.genchi.hibernate.configs;

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
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration
@EntityScan(basePackages = "url.genchi.hibernate.entities")
@EnableJpaRepositories(transactionManagerRef = "readTransactionManager", entityManagerFactoryRef = "readManagerFactory", basePackages = "url.genchi.hibernate.daos.read")

public class ReadEntityManager {
    @Bean
    @ConfigurationProperties(prefix = "datasource.read")
    public DataSource readDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "readManagerFactory")
    public LocalContainerEntityManagerFactoryBean readManagerFactory(final EntityManagerFactoryBuilder builder) {
        return builder.dataSource(readDataSource()).build();
    }

    @Bean(name = "readTransactionManager")
    @Qualifier(value = "readTransactionManager")
    public JpaTransactionManager readTransactionManager(final EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
     }

}
