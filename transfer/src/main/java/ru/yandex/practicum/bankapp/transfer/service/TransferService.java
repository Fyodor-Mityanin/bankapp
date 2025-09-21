package ru.yandex.practicum.bankapp.transfer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.bankapp.api.accounts.api.BalanceChangeRequestDto;
import ru.yandex.practicum.bankapp.api.accounts.client.AccountsClient;
import ru.yandex.practicum.bankapp.api.exchange.api.RateRequestDto;
import ru.yandex.practicum.bankapp.api.exchange.client.ExchangeClient;
import ru.yandex.practicum.bankapp.transfer.controller.TransferController;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransferMapper transferMapper;
    private final AccountsClient accountsClient;
    private final ExchangeClient exchangeClient;

    public void self(TransferController.SelfTransferRequestDto requestDto) {
        BigDecimal rate = BigDecimal.ONE;
        if (!requestDto.from().equals(requestDto.to())) {
            RateRequestDto rateRequestDto = transferMapper.toRateRequestDto(requestDto);
            rate = exchangeClient.getaActualRate(rateRequestDto).rate();
        }
        BalanceChangeRequestDto withdrawRequestDto = transferMapper.toWithdrawRequestDto(requestDto);
        BalanceChangeRequestDto depositRequestDto = transferMapper.toDepositRequestDto(requestDto, rate);

        accountsClient.changeBalance(withdrawRequestDto);
        accountsClient.changeBalance(depositRequestDto);
    }

    public void elsee(TransferController.ElseTransferRequestDto requestDto) {
        BigDecimal rate = BigDecimal.ONE;
        if (!requestDto.from().equals(requestDto.to())) {
            RateRequestDto rateRequestDto = transferMapper.toRateRequestDto(requestDto);
            rate = exchangeClient.getaActualRate(rateRequestDto).rate();
        }
        BalanceChangeRequestDto withdrawRequestDto = transferMapper.toWithdrawRequestDto(requestDto);
        BalanceChangeRequestDto depositRequestDto = transferMapper.toDepositRequestDto(requestDto, rate);

        accountsClient.changeBalance(withdrawRequestDto);
        accountsClient.changeBalance(depositRequestDto);
    }
}
