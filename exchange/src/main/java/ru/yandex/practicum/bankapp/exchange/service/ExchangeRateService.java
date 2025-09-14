package ru.yandex.practicum.bankapp.exchange.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.bankapp.api.exchangegenerator.api.ExchangeRateDto;
import ru.yandex.practicum.bankapp.exchange.controller.CurrencyDto;
import ru.yandex.practicum.bankapp.exchange.entity.Currency;
import ru.yandex.practicum.bankapp.exchange.entity.ExchangeRate;
import ru.yandex.practicum.bankapp.exchange.repository.CurrencyRepository;
import ru.yandex.practicum.bankapp.exchange.repository.ExchangeRateRepository;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;
    private final CurrencyRepository currencyRepository;

    @Transactional
    public void save(ExchangeRateDto exchangeRateDto) {
        Currency currencyFrom = currencyRepository
                .findById(exchangeRateDto.from())
                .orElseThrow();
        Currency currencyTo = currencyRepository
                .findById(exchangeRateDto.to())
                .orElseThrow();
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setCurrencyFrom(currencyFrom);
        exchangeRate.setCurrencyTo(currencyTo);
        exchangeRate.setRate(exchangeRateDto.rate());
        exchangeRateRepository.save(exchangeRate);
    }

    public List<CurrencyDto> getLast() {
        return exchangeRateRepository.findLatestRates("RUB").stream()
                .map(i -> new CurrencyDto(i.getCurrencyTo().getCode(), i.getCurrencyTo().getName(), i.getRate()))
                .sorted(Comparator.comparing(CurrencyDto::name))
                .toList();
    }
}
