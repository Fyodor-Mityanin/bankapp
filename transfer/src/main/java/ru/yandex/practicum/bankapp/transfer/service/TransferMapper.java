package ru.yandex.practicum.bankapp.transfer.service;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.yandex.practicum.bankapp.api.accounts.api.BalanceChangeRequestDto;
import ru.yandex.practicum.bankapp.api.exchange.api.ExchangeRateDto;
import ru.yandex.practicum.bankapp.api.exchange.api.RateRequestDto;
import ru.yandex.practicum.bankapp.transfer.controller.TransferController;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Mapper
public interface TransferMapper {

    RateRequestDto toRateRequestDto(TransferController.TransferRequestDto source);

    @Mapping(target = "currency", source = "source.from")
    @Mapping(target = "action", expression = "java(BalanceChangeRequestDto.Action.MINUS)")
    BalanceChangeRequestDto toWithdrawRequestDto(TransferController.TransferRequestDto source);

    @Mapping(target = "currency", source = "source.to")
    @Mapping(target = "action", expression = "java(BalanceChangeRequestDto.Action.PLUS)")
    @Mapping(target = "value", source = "source.value", qualifiedByName = "getValue")
    BalanceChangeRequestDto toDepositRequestDto(TransferController.TransferRequestDto source, @Context ExchangeRateDto exchangeRateDto);

    @Named("getValue")
    default Double getValue(Double source, @Context ExchangeRateDto exchangeRateDto) {
        BigDecimal result = BigDecimal.valueOf(source)
                .divide(exchangeRateDto.rate(), 2, RoundingMode.HALF_UP);
        return result.doubleValue();
    }

}
