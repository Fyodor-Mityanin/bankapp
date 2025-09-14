package ru.yandex.practicum.bankapp.exchange.controller;

import java.math.BigDecimal;

public record CurrencyDto(String title, String name, BigDecimal value) {
}