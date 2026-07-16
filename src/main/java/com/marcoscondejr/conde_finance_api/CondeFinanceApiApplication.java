package com.marcoscondejr.conde_finance_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.marcoscondejr.conde_finance_api.config.DotenvLoader;

@SpringBootApplication
public class CondeFinanceApiApplication {

	public static void main(String[] args) {
		DotenvLoader.init();
		SpringApplication.run(CondeFinanceApiApplication.class, args);
	}

}
