package ru.yandex.practicum.bankapp.exchangegenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableFeignClients(basePackages = "ru.yandex.practicum.bankapp.api.exchangegenerator.client")
public class ExchangeGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExchangeGeneratorApplication.class, args);
	}
}
