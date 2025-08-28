package ru.yandex.practicum.bankapp.accounts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.bankapp.accounts.controller.AccountRequest;
import ru.yandex.practicum.bankapp.accounts.controller.AccountResponse;
import ru.yandex.practicum.bankapp.accounts.entity.Account;
import ru.yandex.practicum.bankapp.accounts.repository.AccountRepository;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
//    private final NotificationClient notificationClient;


    @Transactional
    public AccountResponse create(AccountRequest request) {
        Account account = accountMapper.toEntity(request);
        Account saved = accountRepository.save(account);
        return accountMapper.toResponse(saved);
    }

    public boolean existsByLogin(String login) {
        return accountRepository.existsByLogin(login);
    }
}
