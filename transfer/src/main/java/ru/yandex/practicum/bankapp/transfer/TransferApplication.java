package ru.yandex.practicum.bankapp.transfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(
        basePackages = {"ru.yandex.practicum.bankapp.api.accounts.client",
                "ru.yandex.practicum.bankapp.api.exchange.client" })
public class TransferApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransferApplication.class, args);
    }
}
