package com.springbatch.remotechunkingjob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class RemoteChunkingJobApplication {

	public static void main(String[] args) {
		System.out.println(Arrays.toString(args));
		ConfigurableApplicationContext context = SpringApplication.run(RemoteChunkingJobApplication.class, args);
		if(Arrays.toString(args).contains("manager")) context.close();
	}

}
