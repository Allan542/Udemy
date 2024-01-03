package com.springbatch.simplepartitionerlocal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class SimplePartitionerLocalJobApplication {

	public static void main(String[] args) {
//		Scanner scanner = new Scanner(System.in);
//		while (true){
//			String next = scanner.next();
//			if(next.equals("sim")) break;
//		}
		ConfigurableApplicationContext context = SpringApplication.run(SimplePartitionerLocalJobApplication.class, args);
		context.close();
	}

}
