package ru.yandex.practicum.bankapp.api.exchangegenerator.api;

import java.math.BigDecimal;

public record ExchangeRate(String from, String to, BigDecimal rate) {
}
