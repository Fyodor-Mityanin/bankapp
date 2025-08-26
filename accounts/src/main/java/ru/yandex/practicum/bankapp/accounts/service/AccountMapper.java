package ru.yandex.practicum.bankapp.accounts.service;

import org.mapstruct.Mapper;
import ru.yandex.practicum.bankapp.accounts.controller.AccountRequest;
import ru.yandex.practicum.bankapp.accounts.controller.AccountResponse;
import ru.yandex.practicum.bankapp.accounts.entity.Account;

@Mapper
public interface AccountMapper {

    AccountResponse toResponse(Account source);
    Account toEntity(AccountRequest source);
}
