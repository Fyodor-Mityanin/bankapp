package ru.yandex.practicum.bankapp.accounts.controller;

import java.time.LocalDate;
import java.util.List;

public record AccountEditRequest(String name,  LocalDate birthdate, List<String> account) {
}
