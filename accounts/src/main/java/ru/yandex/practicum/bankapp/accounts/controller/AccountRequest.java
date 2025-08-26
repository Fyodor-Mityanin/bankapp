package ru.yandex.practicum.bankapp.accounts.controller;

import java.util.List;

public record AccountRequest(String login, String password, List<BalanceDto> balances) {
    public record BalanceDto(String currency, Double amount) {}
}
