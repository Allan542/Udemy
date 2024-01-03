package com.springbatch.migracaodados;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MultithreadingStepApplication {

	public static void main(String[] args) {
		// Por causa da pool de threads que continua viva, a gente precisa vir na classe main e fechar o contexto da aplicação quando o job for encerrado completamente, já que não é uma aplicação schedulada. É usado dessa forma porque a interface ApplicationContext não possui o método close
		ConfigurableApplicationContext run = SpringApplication.run(MultithreadingStepApplication.class, args);
		run.close();
	}

}
