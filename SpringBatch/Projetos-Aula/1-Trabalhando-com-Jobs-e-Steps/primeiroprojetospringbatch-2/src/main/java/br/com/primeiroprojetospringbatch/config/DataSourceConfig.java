package br.com.primeiroprojetospringbatch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.List;

@Configuration // Permite a configuração de múltiplos bancos
public class DataSourceConfig {

    @Primary // Quando não informado um qualificador, é usado sempre o do spring batch
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource") // Data source do spring batch dentro da properties
    public DataSource springDataSouce(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "app.datasource") // Data source da aplicação dentro da properties
    public DataSource appDataSouce(){
        return DataSourceBuilder.create().build();
    }
}
