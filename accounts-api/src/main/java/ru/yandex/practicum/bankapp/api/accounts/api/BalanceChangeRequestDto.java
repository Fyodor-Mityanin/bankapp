package ru.yandex.practicum.bankapp.api.accounts.api;

public record BalanceChangeRequestDto(
        String login, String currency, Double value, Action action
) {
    public enum Action {
        PLUS,
        MINUS
    }
}
