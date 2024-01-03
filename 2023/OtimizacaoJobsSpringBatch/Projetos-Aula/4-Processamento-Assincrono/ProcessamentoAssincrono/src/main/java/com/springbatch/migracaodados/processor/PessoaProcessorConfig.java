package com.springbatch.migracaodados.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.springbatch.migracaodados.dominio.Pessoa;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class PessoaProcessorConfig  {
  Logger logger = LoggerFactory.getLogger(PessoaProcessorConfig.class);
  private static final RestTemplate restTemplate = new RestTemplate();

  @Bean
  public AsyncItemProcessor<Pessoa, Pessoa> asyncPessoaProcessor() throws Exception {
    AsyncItemProcessor<Pessoa, Pessoa> processor = new AsyncItemProcessor<>();
    processor.setDelegate(pessoaProcessor());
    processor.setTaskExecutor(taskExecutor());
    return processor;
  }

  @Bean
  public TaskExecutor taskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(8);
    executor.setMaxPoolSize(8);
    executor.setQueueCapacity(8);
    executor.setThreadNamePrefix("MultiThreaded-");
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); // É lançado uma exceção caso chegue uma tarefa e não tenha uma thread para atendê-la. Esse comando não vai permitir isso, fazendo com que o programa continue a execução normalmente. A thread que está executando a requisição de processamento é a que vai fazer o processamento em si. Não é necessário esperar a ThreadPool ser liberada
    return executor;
  }

  public ItemProcessor<Pessoa, Pessoa> pessoaProcessor() throws Exception {
    return pessoa -> {
      try {
        String uri = String.format("http://my-json-server.typicode.com/giuliana-bezerra/demo/profile/%d", pessoa.getId());
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
      } catch (RestClientResponseException e) {
        logger.info("{}", pessoa.getId());
      }
      return pessoa;
    };
  }
}
