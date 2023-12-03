package br.com.primeiroprojetospringbatch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

@Configuration // Para ambiente produtivo, está pegando properties de pasta externa para não permitir dados sensíveis dentro da aplicação
public class PropsConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer config(){
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocation(new FileSystemResource("C:/Users/Allan Carlos/OneDrive/Documents/Pessoal/Estudos/2023/Udemy/SpringBatch/Arquivos-Deploy-Prod/primeiroprojetospringbatch/application.properties"));
        return configurer;
    }
}
