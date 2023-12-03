package com.springbatch.migracaodados.reader;

import com.springbatch.migracaodados.dominio.DadosBancarios;
import com.springbatch.migracaodados.dominio.Pessoa;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.util.Date;

@Configuration
public class ArquivoDadosBancariosReaderConfig {

    @Bean
    public FlatFileItemReader<DadosBancarios> dadosBancariosReader() {
        return new FlatFileItemReaderBuilder<DadosBancarios>()
            .name("dadosBancariosReader")
            .resource(new FileSystemResource("files/dados_bancarios.csv"))
            .delimited()
            .names("pessoaId", "agencia", "conta", "banco", "id")
            .addComment("--") // Ignora as linhas com comentários que tenham "--"
            .targetType(DadosBancarios.class)
            .build();
    }
}
