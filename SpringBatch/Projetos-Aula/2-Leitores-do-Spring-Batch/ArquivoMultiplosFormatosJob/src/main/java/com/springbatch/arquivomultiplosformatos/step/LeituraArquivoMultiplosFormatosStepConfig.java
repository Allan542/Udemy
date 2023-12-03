package com.springbatch.arquivomultiplosformatos.step;

import com.springbatch.arquivomultiplosformatos.reader.ArquivoClienteTransacaoReader;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LeituraArquivoMultiplosFormatosStepConfig {
	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	public Step leituraArquivoMultiplosFormatosStep(
			FlatFileItemReader leituraArquivoMultiplosFormatosReader,
			ItemWriter leituraArquivoMultiplosFormatosItemWriter) {
		return stepBuilderFactory
				.get("leituraArquivoMultiplosFormatosStep")
				.chunk(1) // Passa o FlatFileItemReader para esse cara que foi criado com o intuito de associar o Cliente e as Transações
				.reader(new ArquivoClienteTransacaoReader(leituraArquivoMultiplosFormatosReader)) // Antes era um leitor composto que escolheria qual padrão de linha ele usaria, agora criou-se um leitor customizado para que ele encapsule as transações no cliente
				.writer(leituraArquivoMultiplosFormatosItemWriter)
				.build();
	}
}
