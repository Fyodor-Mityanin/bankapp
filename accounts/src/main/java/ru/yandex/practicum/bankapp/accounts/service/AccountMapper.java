package ru.yandex.practicum.bankapp.accounts.service;

import lombok.Setter;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.yandex.practicum.bankapp.accounts.controller.AccountRequest;
import ru.yandex.practicum.bankapp.accounts.controller.AccountResponse;
import ru.yandex.practicum.bankapp.accounts.controller.UserResponse;
import ru.yandex.practicum.bankapp.accounts.entity.Account;
import ru.yandex.practicum.bankapp.accounts.entity.AccountBalance;

import java.util.List;
import java.util.Map;

@Mapper
public abstract class AccountMapper {

    private static final Map<String, String> CURRENCY_MAP =
            Map.of("RUB", "Рубль", "USD", "Доллар", "CNY", "Юань");
    protected static final List<AccountResponse.CurrencyDto> CURRENCY_DTO_LIST =
            CURRENCY_MAP.entrySet().stream()
                    .map(i -> new AccountResponse.CurrencyDto(i.getKey(), i.getValue()))
                    .toList();

    @Setter(onMethod_ = @Autowired)
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "name", source = "fullName")
    @Mapping(target = "accounts", source = "balances")
    @Mapping(target = "currencies", expression = "java(CURRENCY_DTO_LIST)")
    abstract AccountResponse toResponse(Account source);

    @Mapping(target = "balances", ignore = true)
    @Mapping(target = "fullName", source = "name")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "password", qualifiedByName = "encodePassword")
    abstract Account toEntity(AccountRequest source);

    @Mapping(target = "name", source = "fullName")
    abstract UserResponse toUserResponse(Account source);

    @Named("encodePassword")
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    protected AccountResponse.BalanceDto toResponse(AccountBalance source) {
        AccountResponse.CurrencyDto currencyDto = new AccountResponse.CurrencyDto(source.getCurrency(), CURRENCY_MAP.get(source.getCurrency()));
        return new AccountResponse.BalanceDto(source.getEnable(), currencyDto, source.getAmount());
    }

    @AfterMapping
    void afterMapping(@MappingTarget Account target, AccountRequest source) {
        List<AccountBalance> list = CURRENCY_MAP.keySet().stream()
                .map(key -> {
                    AccountBalance accountBalance = new AccountBalance();
                    accountBalance.setCurrency(key);
                    accountBalance.setAccount(target);
                    accountBalance.setEnable(false);
                    accountBalance.setAmount(0.0);
                    return accountBalance;
                })
                .toList();
        target.getBalances().addAll(list);
    }
}
