package ru.yandex.practicum.bankapp.accounts.service;

import lombok.Setter;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.yandex.practicum.bankapp.accounts.controller.AccountRequest;
import ru.yandex.practicum.bankapp.accounts.controller.AccountResponse;
import ru.yandex.practicum.bankapp.accounts.entity.Account;
import ru.yandex.practicum.bankapp.accounts.entity.AccountBalance;

import java.util.List;
import java.util.Map;

@Mapper
public abstract class AccountMapper {

    private static final Map<String, String> currencyMap =
            Map.of("RUB", "Рубль", "USD", "Доллар", "CNY", "Юань");

    @Setter(onMethod_ = @Autowired)
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "name", source = "fullName")
    @Mapping(target = "accounts", source = "balances")
    abstract AccountResponse toResponse(Account source);

    @Mapping(target = "balances", ignore = true)
    @Mapping(target = "fullName", source = "name")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "password", qualifiedByName = "encodePassword")
    abstract Account toEntity(AccountRequest source);

    @Named("encodePassword")
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

   @AfterMapping
    public void addEmptyBalances(@MappingTarget Account target) {
       List<AccountBalance> list = currencyMap.keySet().stream()
               .map(i -> {
                   AccountBalance accountBalance = new AccountBalance();
                   accountBalance.setCurrency(i);
                   accountBalance.setAmount(0.0);
                   accountBalance.setAccount(target);
                   return accountBalance;
               })
               .toList();
       target.getBalances().addAll(list);
   }

    protected AccountResponse.BalanceDto toResponse(AccountBalance source) {
        AccountResponse.CurrencyDto currencyDto = new AccountResponse.CurrencyDto(source.getCurrency(), currencyMap.get(source.getCurrency()));
        return  new AccountResponse.BalanceDto(source.getEnable(), currencyDto, source.getAmount());
    }
}
