package com.example.demo;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@EnableTransactionManagement
public class PersistenceConfig {

    @Resource
    private Environment env;
    private static final String MYSQL_CONN_URL = "jdbc:mysql://%s:%s/%s";






    @Bean
    @Primary
    public DataSource getDataSourceConfig() {
        HikariDataSource dataSource = (HikariDataSource) DataSourceBuilder
                .create()
                .driverClassName(env.getRequiredProperty("spring.datasource.driver-class-name"))
                .url(String.format(String.format(MYSQL_CONN_URL, env.getProperty("DB_HOSTNAME"),env.getProperty("DB_PORT"), "CabsDetailsDB?useSSL=false&autoReconnect=true&createDatabaseIfNotExist=true&&nullNamePatternMatchesAll=true")))
                .username(env.getRequiredProperty("DB_USERNAME"))
                .password(env.getRequiredProperty("DB_PASSWORD"))
                .build();
        dataSource.setIdleTimeout(300000);
        dataSource.setMaxLifetime(2000000);
        System.out.println(dataSource);
        return dataSource;
    }




    @Bean
    @Primary
    public LocalSessionFactoryBean getSessionFactoryConfig(Properties properties) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(getDataSourceConfig());
        sessionFactory.setPackagesToScan(new String[]{ "com.example.demo.entities"});
        sessionFactory.setHibernateProperties(properties);
        return sessionFactory;
    }


    @Bean
    @Primary
    public Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put(AvailableSettings.DIALECT, env.getRequiredProperty("hibernate.dialect"));
        properties.put(AvailableSettings.SHOW_SQL, env.getRequiredProperty("hibernate.show_sql"));
        properties.put(AvailableSettings.STATEMENT_BATCH_SIZE, env.getRequiredProperty("hibernate.batch.size"));
        properties.put(AvailableSettings.HBM2DDL_AUTO, env.getRequiredProperty("hibernate.hbm2ddl.auto"));
        properties.put(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, env.getRequiredProperty("hibernate.current.session.context.class"));
        properties.put(AvailableSettings.USE_SECOND_LEVEL_CACHE,env.getRequiredProperty("hibernate.cache.use_second_level_cache"));
        return properties;
    }



    @Bean
    public HibernateTransactionManager transactionManagerConfig(@Qualifier("getSessionFactoryConfig") SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }








}
