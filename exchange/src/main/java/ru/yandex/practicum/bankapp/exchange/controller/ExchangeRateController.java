package ru.yandex.practicum.bankapp.exchange.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.bankapp.api.exchangegenerator.api.ExchangeRateDto;
import ru.yandex.practicum.bankapp.exchange.service.ExchangeRateService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/rates")
@RequiredArgsConstructor
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    @PostMapping
    void saveRate(@RequestBody ExchangeRateDto rate) {
        exchangeRateService.save(rate);
    }

    @GetMapping
    List<CurrencyDto> getRates() {
        return exchangeRateService.getLast();
    }
}
