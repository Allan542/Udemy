package com.udemy.parimparjob.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class PropsConfig {

    @Bean
    public PropertySourcesPlaceholderConfigurer config(){
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocation(new FileSystemResource("C:/Users/Allan Carlos/OneDrive/Documents/Pessoal/Estudos/2023/Udemy/SpringBatch/Arquivos-Deploy-Prod/parimparjob/application.properties"));
        return configurer;
    }
}
