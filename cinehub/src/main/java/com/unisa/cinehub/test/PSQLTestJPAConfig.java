package com.unisa.cinehub.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = {"com.unisa.cinehub.data.repository"})
@EnableTransactionManagement
public class PSQLTestJPAConfig {

    @Bean
    @Profile("test")
    public DataSource dataSource(){
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("org.h2.Driver");
        driverManagerDataSource.setUrl("jdbc:postgresql://localhost:5432/cinehubtesting");
        driverManagerDataSource.setUsername("postgres");
        driverManagerDataSource.setPassword("password");

        return driverManagerDataSource;
    }
}
