package com.geekbrains;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class PersistenceConfig {

    @Bean(name="dataSource")
    public DataSource dataSource() {
        // === h2db + Hikary (пример с урока) ===
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setJdbcUrl("jdbc:h2:./test");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

/*
        // === PostgreSQL + jdbc.datasource ===
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/geekshop");
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");
*/
/*
        // === MySQL + jdbc.datasource ===
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/geekshop?createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true&useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
*/

        return dataSource;
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "none");
        hibernateProperties.setProperty("hibernate.show_sql", "false");

        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
//        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL94Dialect");
//        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

        return hibernateProperties;
    }

    @Bean
    public LocalSessionFactoryBean entityManagerFactory() {
        LocalSessionFactoryBean factory = new LocalSessionFactoryBean(); // фабрика
        factory.setDataSource(dataSource()); // источник (БД)
        factory.setPackagesToScan("com.geekbrains.persistence.entities"); // ссылка на классы-сущности
        factory.setHibernateProperties(hibernateProperties()); // настройки Hibernate
        return factory;
    }

    @Bean
    public EntityManager entityManager(SessionFactory sessionFactory) {
        return sessionFactory.createEntityManager();
    }

}