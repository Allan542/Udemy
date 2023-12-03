package com.springbatch.arquivolargurafixa.reader;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.arquivolargurafixa.dominio.Cliente;
import org.springframework.core.io.Resource;

@Configuration
public class LeituraArquivoLarguraFixaReaderConfig {

	@StepScope
	@Bean
	public FlatFileItemReader<Cliente> leituraArquivoLarguraFixaReader(
		@Value("#{jobParameters['arquivoClientes']}") Resource arquivoCLientes) {
		return new FlatFileItemReaderBuilder<Cliente>()
			.name("leituraArquivoLarguraFixaReader") // nome do reader
			.resource(arquivoCLientes) // arquivo que será lido
			.fixedLength() // tipo do arquivo (Largura fixa)
			.columns(new Range[]{new Range(1, 10), new Range(11, 20), new Range(21, 23), new Range(24, 43)}) // tamanho das colunas de cada dado, delimitados por uma lista de Range
			.names("nome", "sobrenome", "idade", "email") // Nome das colunas
			.targetType(Cliente.class) // Mapeia as propriedades na classe-domínio Cliente
			.build();
	}
}
