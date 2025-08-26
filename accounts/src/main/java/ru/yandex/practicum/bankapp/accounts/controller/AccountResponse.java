package ru.yandex.practicum.bankapp.accounts.controller;

import java.util.List;

public record AccountResponse(Long id, String login, List<BalanceDto> balances) {
    public record BalanceDto(Long id, String currency, Double amount) {}
}