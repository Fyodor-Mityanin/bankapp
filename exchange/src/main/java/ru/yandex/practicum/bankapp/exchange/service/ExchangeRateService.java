package ru.yandex.practicum.bankapp.exchange.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.bankapp.api.exchangegenerator.api.ExchangeRateDto;
import ru.yandex.practicum.bankapp.exchange.entity.Currency;
import ru.yandex.practicum.bankapp.exchange.entity.ExchangeRate;
import ru.yandex.practicum.bankapp.exchange.repository.CurrencyRepository;
import ru.yandex.practicum.bankapp.exchange.repository.ExchangeRateRepository;

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
}
