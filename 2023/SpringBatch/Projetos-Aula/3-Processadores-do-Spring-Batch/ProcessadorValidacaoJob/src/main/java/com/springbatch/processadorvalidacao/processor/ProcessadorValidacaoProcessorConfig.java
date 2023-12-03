package com.springbatch.processadorvalidacao.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
import org.springframework.batch.item.validator.BeanValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.processadorvalidacao.dominio.Cliente;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ProcessadorValidacaoProcessorConfig {
	private Set<String> emails = new HashSet<>();

	@Bean
	public ItemProcessor<Cliente, Cliente> processadorValidacaoProcessor() throws Exception {
		return new CompositeItemProcessorBuilder<Cliente, Cliente>()
			.delegates(beanValidatingItemProcessor(), emailValidatingProcessor())
			.build();
	}

	private BeanValidatingItemProcessor<Cliente> beanValidatingItemProcessor() throws Exception {
		// Ele recebe só um tipo, porque o tipo ValidatingItemProcessor não altera o dado, só valida e passa para o escritor
		BeanValidatingItemProcessor<Cliente> processor = new BeanValidatingItemProcessor<>();
		processor.setFilter(true); // Se colocado o filtro como true, ele passará apenas os válidos, sem nenhuma tratativa, mas também não interromperá quando jogada uma exceção
		// Quando a gente utilizava o BeanValidatingItemProcessor, o seu ciclo de vida ocorria normalmente, agora como ele está sendo colocado num processador composto, precisa invocar o método que seta as propriedades do processador
		processor.afterPropertiesSet();
		return processor;
	}

	private ValidatingItemProcessor<Cliente> emailValidatingProcessor(){
		ValidatingItemProcessor<Cliente> processor = new ValidatingItemProcessor<>(); // Usar a própria classe validator, possibilita ter o controle na validação
		processor.setValidator(validator()); // Criando um validator customizado
		processor.setFilter(true);
		return processor;
	}

	private Validator<Cliente> validator() {
		return new Validator<Cliente>() {
			@Override
			public void validate(Cliente cliente) throws ValidationException {
				if(emails.contains(cliente.getEmail())){ // Se encontrou um duplicado, não imprimirá ele novamente
					throw new ValidationException(String.format("O cliente %s já foi processado!", cliente.getEmail()));
				}
				emails.add(cliente.getEmail());
			}
		};
	}
}
