package com.springbatch.folhaponto.writer;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.folhaponto.dominio.FolhaPonto;
import org.springframework.core.io.Resource;

@Configuration
public class FolhaPontoTemplateWriterConfig {
	@Bean
	@StepScope
	public FlatFileItemWriter<FolhaPonto> imprimeFolhaPontoWriter(
		@Value("#{jobParameters['folhaPonto']}") Resource resource
	) {
		return new FlatFileItemWriterBuilder<FolhaPonto>()
			.name("imprimeFolhaPontoWriter")
			.resource(resource)
			.headerCallback(imprimeCabecalho())
			.lineAggregator(imprimePontos())
			.footerCallback(imprimeRodape())
			.build();
	}

	private LineAggregator<FolhaPonto> imprimePontos() {
		return folhaPonto -> {
			StringBuilder writer = new StringBuilder();
			writer.append(String.format("----------------------------------------------------------------------------\n"));
			writer.append(String.format("NOME:%s\n", folhaPonto.getNome()));
			writer.append(String.format("MATRICULA:%s\n", folhaPonto.getMatricula()));
			writer.append(String.format("----------------------------------------------------------------------------\n"));
			writer.append(String.format("%10s%10s%10s%10s%10s", "DATA", "ENTRADA", "SAIDA", "ENTRADA", "SAIDA"));

			for (String dataRegistroPonto : folhaPonto.getRegistrosPontos().keySet()) {
				writer.append(String.format("\n%s", dataRegistroPonto));

				for (String registro : folhaPonto.getRegistrosPontos().get(dataRegistroPonto)) {
					writer.append(String.format("%10s", registro));
				}
			}
			
			return writer.toString();
		};

		
	}

	private FlatFileHeaderCallback imprimeCabecalho() {
		return writer -> {
			writer.append(String.format("SISTEMA INTEGRADO: XPTO \t\t\t\t DATA: %s\n",
				new SimpleDateFormat("dd/MM/yyyy").format(new Date())));
			writer.append(String.format("MÓDULO: RH \t\t\t\t\t\t\t\t HORA: %s\n",
				new SimpleDateFormat("HH:MM").format(new Date())));
			writer.append(String.format("\t\t\t\tFOLHA DE PONTO\n"));
		};
	}

	private FlatFileFooterCallback imprimeRodape() {
		return writer -> {
			writer.append(String.format("\n\t\t\t\t\t\t\t  Código de Autenticação: %s\n", "fkyew6868fewjfhjjewf"));
		};
	}
}
