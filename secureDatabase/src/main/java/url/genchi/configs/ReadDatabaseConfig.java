package url.genchi.configs;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "url.genchi.models")
public class ReadDatabaseConfig {

    @Value("${db.driver}")
    private String DB_DRIVER;
    
    @Value("${db.readpassword}")
    private String DB_READPASSWORD;

    @Value("${db.writepassword}")
    private String DB_WRITEPASSWORD;
    
    @Value("${db.url}")
    private String DB_URL;
    
    @Value("${db.readusername}")
    private String DB_READUSERNAME;
    
    @Value("${db.writeusername}")
    private String DB_WRITEUSERNAME;

    @Value("${hibernate.dialect}")
    private String HIBERNATE_DIALECT;
    
    @Value("${hibernate.show_sql}")
    private String HIBERNATE_SHOW_SQL;
    
    @Value("${hibernate.hbm2ddl.auto}")
    private String HIBERNATE_HBM2DDL_AUTO;

    @Value("${entitymanager.packagesToScan}")
    private String ENTITYMANAGER_PACKAGES_TO_SCAN;

    @Bean
    @Primary
    public DataSource readDataSource() {
        DriverManagerDataSource readDataSource = new DriverManagerDataSource();
        readDataSource.setDriverClassName(DB_DRIVER);
        readDataSource.setUrl(DB_URL);
        readDataSource.setUsername(DB_READUSERNAME);
        readDataSource.setPassword(DB_READPASSWORD);
        return readDataSource;
    }

    @Bean
    public DataSource writeDataSource() {
        DriverManagerDataSource writeDataSource = new DriverManagerDataSource();
        writeDataSource.setDriverClassName(DB_DRIVER);
        writeDataSource.setUrl(DB_URL);
        writeDataSource.setUsername(DB_WRITEUSERNAME);
        writeDataSource.setPassword(DB_WRITEPASSWORD);
        return writeDataSource;
    }

    @Primary
    @Bean
    public LocalSessionFactoryBean readSessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(readDataSource());
        sessionFactoryBean.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", HIBERNATE_DIALECT);
        hibernateProperties.put("hibernate.show_sql", HIBERNATE_SHOW_SQL);
        hibernateProperties.put("hibernate.hbm2fddl.auto", HIBERNATE_HBM2DDL_AUTO);
        sessionFactoryBean.setHibernateProperties(hibernateProperties);
        return sessionFactoryBean;
    }

    @Bean
    public LocalSessionFactoryBean writeSessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(writeDataSource());
        sessionFactoryBean.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", HIBERNATE_DIALECT);
        hibernateProperties.put("hibernate.show_sql", HIBERNATE_SHOW_SQL);
        hibernateProperties.put("hibernate.hbm2fddl.auto", HIBERNATE_HBM2DDL_AUTO);
        sessionFactoryBean.setHibernateProperties(hibernateProperties);
        return sessionFactoryBean;
    }

    @Primary
    @Bean(name = "read")
    public HibernateTransactionManager readTransactionManager() {
        System.out.println("get read hibernate");
        HibernateTransactionManager transactionManager = 
                new HibernateTransactionManager();
        transactionManager.setSessionFactory(readSessionFactory().getObject());
        return transactionManager;
    }

    @Bean(name = "write")
    public HibernateTransactionManager writeTransactionManager() {
        System.out.println("get write hibernate");
        HibernateTransactionManager transactionManager = 
                new HibernateTransactionManager();
        transactionManager.setSessionFactory(writeSessionFactory().getObject());
        return transactionManager;
    }
} // class DatabaseConfig
