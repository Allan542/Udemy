package com.springbatch.enviopromocoesemail.config;

import com.springbatch.enviopromocoesemail.job.EnvioPromocoesClientesScheduleJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean // detalhe dos jobs
    public JobDetail quartzJobDetail(){
        return JobBuilder
            .newJob(EnvioPromocoesClientesScheduleJob.class) // referencia o schedule job para execuções agendadas
            .storeDurably() // mantém os dados das execuções agendadas
            .build();
    }

    @Bean
    public Trigger trigger(){
        SimpleScheduleBuilder simpleScheduleBuilder =
            SimpleScheduleBuilder
                .simpleSchedule() // Agendador simples
                .withIntervalInSeconds(60) // A cada 60s
                .withRepeatCount(2); // Executa mais duas vezes, ou seja 3, porque ele começa a contar do 0

        return TriggerBuilder // Configura para qual job e como disparará o gatilho de execução, que neste caso é para o quartzJobDetail, com os parâmetros da execução citados acima
            .newTrigger()
            .forJob(quartzJobDetail())
            .withSchedule(simpleScheduleBuilder)
            .build();
    }
}
