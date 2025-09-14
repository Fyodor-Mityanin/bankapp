package ru.yandex.practicum.bankapp.accounts.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.bankapp.accounts.entity.Account;
import ru.yandex.practicum.bankapp.accounts.service.AccountService;

@Slf4j
@RestController
@RequestMapping("api/v1/balance")
@RequiredArgsConstructor
public class BalanceController {

    private final AccountService accountService;

    @PostMapping("/deposit")
    public void depositCash(
            @RequestBody CashRequest request,
            Authentication authentication
    ) {
        Account account = (Account) authentication.getPrincipal();
        accountService.editBalance(account, request);
    }

    @PostMapping("/withdraw")
    public void withdrawCash(
            @RequestBody CashRequest request,
            Authentication authentication
    ) {
        Account account = (Account) authentication.getPrincipal();
        accountService.editBalance(account, request);
    }

    public record CashRequest(String currency, Double value) {}
}
