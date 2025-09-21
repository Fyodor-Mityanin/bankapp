package ru.yandex.practicum.bankapp.accounts.controller.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.bankapp.accounts.entity.Account;
import ru.yandex.practicum.bankapp.accounts.service.AccountService;
import ru.yandex.practicum.bankapp.api.exchangegenerator.api.CashRequestDto;

@Slf4j
@RestController
@RequestMapping("api/v1/internal/cash")
@RequiredArgsConstructor
public class CashController {

    private final AccountService accountService;

    @PostMapping("/deposit")
    public void deposit(
            @RequestBody CashRequestDto request,
            Authentication authentication
    ) {
        Account account = (Account) authentication.getPrincipal();
        accountService.editBalance(account, request, AccountService.Operator.PLUS);
    }

    @PostMapping("/withdraw")
    public void withdraw(
            @RequestBody CashRequestDto request,
            Authentication authentication
    ) {
        Account account = (Account) authentication.getPrincipal();
        accountService.editBalance(account, request, AccountService.Operator.MINUS);
    }
}
