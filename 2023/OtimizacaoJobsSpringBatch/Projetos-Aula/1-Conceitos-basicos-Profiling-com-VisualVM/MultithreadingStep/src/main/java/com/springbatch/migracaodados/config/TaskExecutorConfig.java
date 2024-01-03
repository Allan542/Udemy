package com.springbatch.migracaodados.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class TaskExecutorConfig {

    @Bean
    public TaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4); // Número de threads criadas inicialmente quando eu vou inserindo as tarefas
        executor.setQueueCapacity(4); // O tamanho da fila é 4 porque o corePoolSize é 4
        executor.setMaxPoolSize(4); // O Máximo é 4 para não exceder o número inicial de threads
        executor.setThreadNamePrefix("Multithread-");
        return executor;
    }
}
