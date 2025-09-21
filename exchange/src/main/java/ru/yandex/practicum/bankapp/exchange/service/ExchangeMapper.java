package ru.yandex.practicum.bankapp.exchange.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.yandex.practicum.bankapp.api.exchange.api.ExchangeRateDto;
import ru.yandex.practicum.bankapp.exchange.controller.CurrencyDto;
import ru.yandex.practicum.bankapp.exchange.entity.ExchangeRate;

@Mapper
public interface ExchangeMapper {

    @Mapping(target = "from", source = "currencyFrom.code")
    @Mapping(target = "to", source = "currencyTo.code")
    ExchangeRateDto toDto(ExchangeRate exchangeRate);

    @Mapping(target = "title", source = "currencyTo.code")
    @Mapping(target = "name", source = "currencyTo.name")
    @Mapping(target = "value", source = "rate")
    CurrencyDto toCurrencyDto(ExchangeRate exchangeRate);
}
