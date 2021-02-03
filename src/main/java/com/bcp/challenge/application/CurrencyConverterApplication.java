package com.bcp.challenge.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.bcp.challenge.application.config",
		"com.bcp.challenge.application.authorization"})
@EntityScan(basePackages = {
		"com.bcp.challenge.adapter.output.repository.entity",
		"com.bcp.challenge.application.authorization.repository.entity"})
@EnableJpaRepositories(basePackages = {
		"com.bcp.challenge.adapter.output.repository",
		"com.bcp.challenge.application.authorization.repository"})
public class CurrencyConverterApplication {
	public static void main(String[] args) {
		SpringApplication.run(CurrencyConverterApplication.class, args);
	}
}
