package ru.yandex.practicum.bankapp.cash.service;

import org.mapstruct.Mapper;
import ru.yandex.practicum.bankapp.api.accounts.api.BalanceChangeRequestDto;
import ru.yandex.practicum.bankapp.cash.controller.CashController;

@Mapper
public interface CashMapper {

    BalanceChangeRequestDto balanceChangeRequestDto(CashController.CashRequestDto source);
}
