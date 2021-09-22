package com.bank.tabungan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class TabunganApplication {

	public static void main(String[] args) {
		SpringApplication.run(TabunganApplication.class, args);
	}

}
