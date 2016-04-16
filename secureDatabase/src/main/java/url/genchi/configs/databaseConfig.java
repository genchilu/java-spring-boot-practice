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
public class databaseConfig {

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

    private DriverManagerDataSource initBaseDataSource () {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DB_DRIVER);
        dataSource.setUrl(DB_URL);
        return dataSource;
    }

    @Bean
    @Primary
    public DataSource readDataSource() {
        DriverManagerDataSource readDataSource = initBaseDataSource();
        readDataSource.setUsername(DB_READUSERNAME);
        readDataSource.setPassword(DB_READPASSWORD);
        return readDataSource;
    }

    @Bean
    public DataSource writeDataSource() {
        DriverManagerDataSource writeDataSource = initBaseDataSource();
        writeDataSource.setUsername(DB_WRITEUSERNAME);
        writeDataSource.setPassword(DB_WRITEPASSWORD);
        return writeDataSource;
    }

    private void setupSessionFactory(LocalSessionFactoryBean sessionFactoryBean) {
        sessionFactoryBean.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", HIBERNATE_DIALECT);
        hibernateProperties.put("hibernate.show_sql", HIBERNATE_SHOW_SQL);
        hibernateProperties.put("hibernate.hbm2fddl.auto", HIBERNATE_HBM2DDL_AUTO);
        sessionFactoryBean.setHibernateProperties(hibernateProperties);
    }

    @Primary
    @Bean(name = "read")
    public LocalSessionFactoryBean readSessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(readDataSource());
        setupSessionFactory(sessionFactoryBean);
        return sessionFactoryBean;
    }

    @Bean(name = "write")
    public LocalSessionFactoryBean writeSessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(writeDataSource());
        setupSessionFactory(sessionFactoryBean);
        return sessionFactoryBean;
    }

    @Primary
    @Bean(name = "read")
    public HibernateTransactionManager readTransactionManager() {
        HibernateTransactionManager transactionManager = 
                new HibernateTransactionManager();
        transactionManager.setSessionFactory(readSessionFactory().getObject());
        return transactionManager;
    }

    @Bean(name = "write")
    public HibernateTransactionManager writeTransactionManager() {
        HibernateTransactionManager transactionManager = 
                new HibernateTransactionManager();
        transactionManager.setSessionFactory(writeSessionFactory().getObject());
        return transactionManager;
    }
}
