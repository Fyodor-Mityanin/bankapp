package ru.yandex.practicum.bankapp.transfer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.bankapp.api.accounts.api.BalanceChangeRequestDto;
import ru.yandex.practicum.bankapp.api.accounts.client.AccountsClient;
import ru.yandex.practicum.bankapp.api.exchange.api.ExchangeRateDto;
import ru.yandex.practicum.bankapp.api.exchange.api.RateRequestDto;
import ru.yandex.practicum.bankapp.api.exchange.client.ExchangeClient;
import ru.yandex.practicum.bankapp.transfer.controller.TransferController;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransferMapper transferMapper;
    private final AccountsClient accountsClient;
    private final ExchangeClient exchangeClient;

    public void handle(TransferController.TransferRequestDto requestDto) {
        RateRequestDto rateRequestDto = transferMapper.toRateRequestDto(requestDto);
        ExchangeRateDto exchangeRateDto = exchangeClient.getaActualRate(rateRequestDto);

        BalanceChangeRequestDto withdrawRequestDto = transferMapper.toWithdrawRequestDto(requestDto);
        BalanceChangeRequestDto depositRequestDto = transferMapper.toDepositRequestDto(requestDto, exchangeRateDto);

        accountsClient.changeBalance(withdrawRequestDto);
        accountsClient.changeBalance(depositRequestDto);
    }
}
