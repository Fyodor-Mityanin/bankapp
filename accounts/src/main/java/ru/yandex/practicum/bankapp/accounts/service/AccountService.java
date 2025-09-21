package ru.yandex.practicum.bankapp.accounts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.bankapp.accounts.controller.AccountEditRequest;
import ru.yandex.practicum.bankapp.accounts.controller.AccountRequest;
import ru.yandex.practicum.bankapp.accounts.controller.AccountResponse;
import ru.yandex.practicum.bankapp.accounts.entity.Account;
import ru.yandex.practicum.bankapp.accounts.entity.AccountBalance;
import ru.yandex.practicum.bankapp.accounts.repository.AccountRepository;
import ru.yandex.practicum.bankapp.api.exchangegenerator.api.CashRequestDto;

import java.util.HashSet;
import java.util.Set;

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

    public void changePassword(Long id, String password) {
        String encodePassword = accountMapper.encodePassword(password);
        accountRepository.findById(id).ifPresent(account -> {
            account.setPassword(encodePassword);
            accountRepository.save(account);
        });
    }

    @Transactional(readOnly = true)
    public AccountResponse getAccount(Account account) {
        return accountMapper.toResponse(account);
    }

    @Transactional
    public void edit(Account account, AccountEditRequest request) {
        account.setFullName(request.name());
        account.setBirthdate(request.birthdate());
        Set<String> enabled = new HashSet<>(request.account());
        for (AccountBalance balance : account.getBalances()) {
            boolean shouldEnable = enabled.contains(balance.getCurrency());
            boolean canDisable = !shouldEnable && balance.getAmount() == 0;
            balance.setEnable(shouldEnable || !canDisable);
        }
        accountRepository.save(account);
    }

    @Transactional
    public void editBalance(Account account, CashRequestDto request, Operator operator) {
        double delta = switch (operator) {
            case PLUS -> request.value();
            case MINUS -> -request.value();
        };

        account.getBalances().stream()
                .filter(i -> i.getCurrency().equals(request.currency()))
                .findFirst()
                .ifPresent(balance -> balance.setAmount(balance.getAmount() + delta));
        accountRepository.save(account);
    }

    public enum Operator {
        PLUS,
        MINUS
    }
}