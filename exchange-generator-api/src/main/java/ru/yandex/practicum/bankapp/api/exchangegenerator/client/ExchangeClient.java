package ru.yandex.practicum.bankapp.api.exchangegenerator.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import ru.yandex.practicum.bankapp.api.exchangegenerator.api.ExchangeRateDto;

@FeignClient(
        name = "exchangeClient",
        url = "${exchange.service.url:http://localhost:8082}"
)
public interface ExchangeClient {

    @PostMapping("/api/v1/rates")
    void sendRate(ExchangeRateDto rate);
}
