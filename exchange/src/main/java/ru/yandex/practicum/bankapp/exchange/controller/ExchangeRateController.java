package ru.yandex.practicum.bankapp.exchange.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.bankapp.api.exchangegenerator.api.ExchangeRateDto;
import ru.yandex.practicum.bankapp.exchange.service.ExchangeRateService;

@Slf4j
@RestController
@RequestMapping("api/v1/rates")
@RequiredArgsConstructor
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;
    @PostMapping
    void saveRate(ExchangeRateDto rate) {
        exchangeRateService.save(rate);
    }
}
