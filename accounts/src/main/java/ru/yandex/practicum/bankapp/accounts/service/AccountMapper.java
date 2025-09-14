package ru.yandex.practicum.bankapp.accounts.service;

import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.yandex.practicum.bankapp.accounts.controller.AccountRequest;
import ru.yandex.practicum.bankapp.accounts.controller.AccountResponse;
import ru.yandex.practicum.bankapp.accounts.entity.Account;
import ru.yandex.practicum.bankapp.accounts.entity.AccountBalance;

import java.util.Map;

@Mapper
public abstract class AccountMapper {

    private static final Map<String, String> currencyMap =
            Map.of("RUB", "Рубль", "USD", "Доллар", "CNY", "Юань");

    @Setter(onMethod_ = @Autowired)
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "accounts", source = "balances")
    abstract AccountResponse toResponse(Account source);

    @Mapping(target = "balances", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "password", qualifiedByName = "encodePassword")
    abstract Account toEntity(AccountRequest source);

    @Named("encodePassword")
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    protected AccountResponse.BalanceDto toResponse(AccountBalance source) {
        AccountResponse.CurrencyDto currencyDto = new AccountResponse.CurrencyDto(source.getCurrency(), currencyMap.get(source.getCurrency()));
        return  new AccountResponse.BalanceDto(true, currencyDto, source.getAmount());
    }
}
