package com.myworkflow.server.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * created by: Vitalii Pasichnyk
 * creation date: 3/6/2017
 * email: code.crosser@gmail.com
 */
@Configuration
@EnableJpaRepositories("com.myworkflow.server.repository")
@EnableTransactionManagement
@PropertySource("classpath:db.properties")
@ComponentScan("com.myworkflow.server.entity")
public class DataBaseConfig {

    @Resource
    private Environment env;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan(env.getRequiredProperty("db.entity.package"));
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setJpaProperties(getHibernateProperties());

        return entityManagerFactoryBean;
    }

    @Bean
    public DataSource dataSource(){
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setUrl(env.getRequiredProperty("db.url"));
        dataSource.setDriverClassName(env.getRequiredProperty("db.driver"));
        dataSource.setUsername(env.getRequiredProperty("db.username"));
        dataSource.setPassword(env.getRequiredProperty("db.password"));

        dataSource.setInitialSize(Integer.valueOf(env.getRequiredProperty("db.initialSize")));
        dataSource.setMinIdle(Integer.valueOf(env.getRequiredProperty("db.minIdle")));
        dataSource.setMaxIdle(Integer.valueOf(env.getRequiredProperty("db.maxIdle")));
        dataSource.setTimeBetweenEvictionRunsMillis(Integer.valueOf(env.getRequiredProperty("db.timeBetweenEvictionRunsMillis")));
        dataSource.setMinEvictableIdleTimeMillis(Integer.valueOf(env.getRequiredProperty("db.minEvictableIdleTimeMillis")));
        dataSource.setTestOnBorrow(Boolean.valueOf(env.getRequiredProperty("db.testOnBorrow")));
        dataSource.setValidationQuery(env.getRequiredProperty("db.validationQuery"));

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(){
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(entityManagerFactory().getObject());

        return manager;
    }

    private Properties getHibernateProperties() {
        try {
            Properties properties = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("hibernate.properties");
            properties.load(inputStream);

            return properties;
        }catch (IOException e){
            throw new IllegalArgumentException("cant find hibernate props file", e);
        }
    }
}
