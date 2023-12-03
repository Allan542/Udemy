package com.springbatch.contasbancarias.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.contasbancarias.dominio.Cliente;
import com.springbatch.contasbancarias.dominio.Conta;

@Configuration
public class CriacaoContasStepConfig {
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step criacaoContasStep(
		ItemReader<Cliente> leituraClientesReader,
		ItemProcessor<Cliente, Conta> geracaoContaProcessor,
		ClassifierCompositeItemWriter<Conta> classifierContaWriter, // Quando usa esse classificado, é necessário registrar os "streams" deste step, ou os writers que representam recursos que precisam ser abertos e fechados no spring
		@Qualifier("fileContaWriter") FlatFileItemWriter<Conta> clienteValidoWriter, // Colocar qualificador para diferenciar ambos os streams do writer
		@Qualifier("clienteInvalidoWriter") FlatFileItemWriter<Conta> clienteInvalidoWriter) {
		return stepBuilderFactory
				.get("criacaoContasStep")
				.<Cliente, Conta>chunk(100) // Otimizar o valor do chunk quando estiver trabalhando com banco de dados
				.reader(leituraClientesReader)
				.processor(geracaoContaProcessor)
				.writer(classifierContaWriter)
				.stream(clienteValidoWriter)
				.stream(clienteInvalidoWriter) // São colocados aqui já que Classifier não possui ItemStreamWriter, para gerenciar abertura e fechamento dos recursos
				.build();
	}
}
