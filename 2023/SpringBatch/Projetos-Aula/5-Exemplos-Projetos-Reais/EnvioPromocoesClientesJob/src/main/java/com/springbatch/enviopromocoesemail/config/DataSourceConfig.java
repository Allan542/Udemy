package com.springbatch.enviopromocoesemail.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSource springDSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("app.datasource")
    public DataSource appDataSource(){
        return DataSourceBuilder.create().build();
    }
}
