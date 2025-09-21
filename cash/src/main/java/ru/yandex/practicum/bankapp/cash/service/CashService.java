package ru.yandex.practicum.bankapp.cash.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.bankapp.api.exchangegenerator.api.CashRequestDto;
import ru.yandex.practicum.bankapp.api.exchangegenerator.client.AccountsClient;

@Service
@RequiredArgsConstructor
public class CashService {

    private final AccountsClient accountsClient;


    public void deposit(CashRequestDto requestDto) {

    }
}
