package ru.yandex.practicum.bankapp.api.exchange.api;

import java.math.BigDecimal;

public record ExchangeRateDto(String from, String to, BigDecimal rate) {
}
