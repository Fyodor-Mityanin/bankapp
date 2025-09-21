package ru.yandex.practicum.bankapp.transfer.service;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.yandex.practicum.bankapp.api.accounts.api.BalanceChangeRequestDto;
import ru.yandex.practicum.bankapp.api.exchange.api.RateRequestDto;
import ru.yandex.practicum.bankapp.transfer.controller.TransferController;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Mapper
public interface TransferMapper {

    RateRequestDto toRateRequestDto(TransferController.SelfTransferRequestDto source);

    RateRequestDto toRateRequestDto(TransferController.ElseTransferRequestDto source);

    @Mapping(target = "currency", source = "source.from")
    @Mapping(target = "action", expression = "java(BalanceChangeRequestDto.Action.MINUS)")
    BalanceChangeRequestDto toWithdrawRequestDto(TransferController.SelfTransferRequestDto source);

    @Mapping(target = "login", source = "source.loginFrom")
    @Mapping(target = "currency", source = "source.from")
    @Mapping(target = "action", expression = "java(BalanceChangeRequestDto.Action.MINUS)")
    BalanceChangeRequestDto toWithdrawRequestDto(TransferController.ElseTransferRequestDto source);

    @Mapping(target = "currency", source = "source.to")
    @Mapping(target = "action", expression = "java(BalanceChangeRequestDto.Action.PLUS)")
    @Mapping(target = "value", source = "source.value", qualifiedByName = "getValue")
    BalanceChangeRequestDto toDepositRequestDto(TransferController.SelfTransferRequestDto source, @Context BigDecimal rate);

    @Mapping(target = "login", source = "source.loginTo")
    @Mapping(target = "currency", source = "source.to")
    @Mapping(target = "action", expression = "java(BalanceChangeRequestDto.Action.PLUS)")
    @Mapping(target = "value", source = "source.value", qualifiedByName = "getValue")
    BalanceChangeRequestDto toDepositRequestDto(TransferController.ElseTransferRequestDto source, @Context BigDecimal rate);

    @Named("getValue")
    default Double getValue(Double source, @Context BigDecimal rate) {
        BigDecimal result = BigDecimal.valueOf(source)
                .divide(rate, 2, RoundingMode.HALF_UP);
        return result.doubleValue();
    }

}
