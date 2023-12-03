package com.udemy.DemonstrativoOrcamentarioJob.reader;

import com.udemy.DemonstrativoOrcamentarioJob.dominio.DemonstrativoOrcamentario;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.MultiResourceItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class MultiplosDemonstrativosOrcamentariosReaderConfig {

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean
    @StepScope
    public MultiResourceItemReader<DemonstrativoOrcamentario> multiplosDemonstrativosOrcamentariosItemReader(
        @Value("#{jobParameters['arquivosLancamentos']}") Resource[] resources,
        FlatFileItemReader<DemonstrativoOrcamentario> flatFileItemReader
        ){
        return new MultiResourceItemReaderBuilder()
            .name("multiplosDemonstrativosOrcamentariosItemReader")
            .resources(resources)
            .delegate(new UnificadorDemonstrativoOrcamentarioReader(flatFileItemReader))
            .build();
    }
}
