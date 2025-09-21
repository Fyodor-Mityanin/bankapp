package ru.yandex.practicum.bankapp.api.exchange.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import ru.yandex.practicum.bankapp.api.exchange.api.ExchangeRateDto;
import ru.yandex.practicum.bankapp.api.exchange.api.RateRequestDto;

@FeignClient(
        name = "exchangeClient",
        url = "${exchange.service.url:http://localhost:8082}"
)
public interface ExchangeClient {

    @PostMapping("/api/v1/rates")
    void sendRate(ExchangeRateDto request);

    @PostMapping("/api/v1/rates")
    void getRate(ExchangeRateDto request);

    @PostMapping("/api/v1/rates/actual")
    ExchangeRateDto getaActualRate(RateRequestDto request);
}
