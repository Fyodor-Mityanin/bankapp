package ru.yandex.practicum.bankapp.accounts.controller;

import java.time.LocalDate;
import java.util.List;

public record AccountResponse(
        Long id,
        String login,
        String name,
        LocalDate birthdate,
        List<BalanceDto> accounts,
        List<CurrencyDto> currencies
) {
    public record BalanceDto(boolean enabled, CurrencyDto currency, Double value) {
    }

    public record CurrencyDto(String name, String title) {
    }
}