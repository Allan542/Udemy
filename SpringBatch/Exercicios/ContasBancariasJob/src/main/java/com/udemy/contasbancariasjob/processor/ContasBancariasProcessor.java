package com.udemy.contasbancariasjob.processor;

import com.udemy.contasbancariasjob.dominio.Cliente;
import com.udemy.contasbancariasjob.dominio.Conta;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
import org.springframework.batch.item.validator.BeanValidatingItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContasBancariasProcessor {

    @Bean
    public ItemProcessor<Cliente, Conta> contasBancariasProcessador() throws Exception {
        return new CompositeItemProcessorBuilder<Cliente, Conta>()
            .delegates(beanValidatingItemProcessor(), contaItemProcessor())
            .build();
    }

    private ItemProcessor<Cliente,Conta> contaItemProcessor() {
        return new ContaItemProcessor();
    }

    private BeanValidatingItemProcessor<Cliente> beanValidatingItemProcessor() throws Exception {
        BeanValidatingItemProcessor<Cliente> processor = new BeanValidatingItemProcessor<>();
        processor.setFilter(true);
        processor.afterPropertiesSet();
        return processor;
    }
}
