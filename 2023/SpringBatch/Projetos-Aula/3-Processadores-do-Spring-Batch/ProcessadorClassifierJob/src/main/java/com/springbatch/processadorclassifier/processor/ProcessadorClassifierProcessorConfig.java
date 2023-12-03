package com.springbatch.processadorclassifier.processor;

import com.springbatch.processadorclassifier.dominio.Cliente;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemProcessorBuilder;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessadorClassifierProcessorConfig {
	@SuppressWarnings({"rawtypes", "unchecked"})
	@Bean
	public ItemProcessor processadorClassifierProcessor() {
		return new ClassifierCompositeItemProcessorBuilder() // Aqui não é necessário declarar tipos, por causa do arquivo múltiplo
			.classifier(classifier()) // Classificador a gente define para ele decidir qual processador ele deve chamar de acordo com a regra de negócio, neste caso, se é um cliente ou uma transação
			.build();
	}

	@SuppressWarnings({"rawtypes"})
	private Classifier classifier() {
		return new Classifier<Object, ItemProcessor>() { // Recebe um objeto e devolve um ItemProcessor dependendo do objeto que está sendo enviado
			@Override
			public ItemProcessor classify(Object objeto) {
				if(objeto instanceof Cliente){
					return new ClienteProcessor(); // Manda para o processor de cliente
				} else {
					return new TransacaoProcessor(); // Manda para o processor de transações
				}
			}
		};
	}
}
