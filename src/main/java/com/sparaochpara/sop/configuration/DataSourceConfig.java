package com.sparaochpara.sop.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:postgresql://sop.postgres.database.azure.com:5432/sparaochpara?sslmode=require");
        dataSource.setUsername("sop");
        dataSource.setPassword("SparaOchPara!");
        dataSource.setDriverClassName("org.postgresql.Driver");
        return dataSource;
    }

}
