package ru.yandex.practicum.bankapp.cash.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.bankapp.api.accounts.api.BalanceChangeRequestDto;
import ru.yandex.practicum.bankapp.api.accounts.client.AccountsClient;
import ru.yandex.practicum.bankapp.cash.controller.CashController;

@Service
@RequiredArgsConstructor
public class CashService {

    private final CashMapper cashMapper;
    private final AccountsClient accountsClient;

    public void handleCash(CashController.CashRequestDto requestDto) {
        BalanceChangeRequestDto balanceChangeRequestDto = cashMapper.balanceChangeRequestDto(requestDto);
        accountsClient.changeBalance(balanceChangeRequestDto);
    }
}
