package com.udemy.DemonstrativoOrcamentarioJob.reader;

import com.udemy.DemonstrativoOrcamentarioJob.dominio.DemonstrativoOrcamentario;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class DemonstrativoOrcamentarioReaderConfig {

    @StepScope
    @Bean
    public FlatFileItemReader<DemonstrativoOrcamentario> demonstrativoOrcamentarioItemReader(
        @Value("") Resource resource
    ){
        return new FlatFileItemReaderBuilder<DemonstrativoOrcamentario> ()
            .name("demonstrativoOrcamentarioItemReader")
            .resource(resource)
            .delimited()
            .names("codigoNaturezaDespesa", "descricaoNaturezaDespesa", "demonstrativoList[0].descricaoLancamento", "demonstrativoList[0].dataLancamento", "demonstrativoList[0].valorLancamento")
            .targetType(DemonstrativoOrcamentario.class)
            .build();
    }
}
