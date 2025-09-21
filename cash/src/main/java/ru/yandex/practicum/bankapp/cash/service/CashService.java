package ru.yandex.practicum.bankapp.cash.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.bankapp.api.accounts.api.CashRequestDto;
import ru.yandex.practicum.bankapp.api.accounts.client.AccountsClient;

@Service
@RequiredArgsConstructor
public class CashService {

    private final AccountsClient accountsClient;

    public void deposit(CashRequestDto requestDto) {
        accountsClient.deposit(requestDto);
    }

    public void withdraw(CashRequestDto requestDto) {
        accountsClient.withdraw(requestDto);
    }
}
