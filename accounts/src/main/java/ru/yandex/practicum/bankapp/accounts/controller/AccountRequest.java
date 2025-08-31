package ru.yandex.practicum.bankapp.accounts.controller;

import java.time.LocalDate;

public record AccountRequest(
        String login,
        String password,
        String confirmPassword,
        String name,
        LocalDate birthdate
) {
}
