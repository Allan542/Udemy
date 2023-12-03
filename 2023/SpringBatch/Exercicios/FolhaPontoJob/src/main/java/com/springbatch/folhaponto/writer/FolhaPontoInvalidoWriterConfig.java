package com.springbatch.folhaponto.writer;

import com.springbatch.folhaponto.dominio.FolhaPonto;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class FolhaPontoInvalidoWriterConfig {

    @Bean
    @StepScope
    public FlatFileItemWriter<FolhaPonto> imprimeFolhaPontoInvalido(
        @Value("#{jobParameters['pontoInvalido']}") Resource resource
    ){
        return new FlatFileItemWriterBuilder<FolhaPonto>()
            .name("imprimeFolhaPontoInvalido")
            .resource(resource)
            .lineAggregator(lineAggregator())
            .lineSeparator("\n")
            .build();
    }

    private LineAggregator<FolhaPonto> lineAggregator() {
        return folhaPonto -> String.valueOf(folhaPonto.getMatricula());
    }
}
