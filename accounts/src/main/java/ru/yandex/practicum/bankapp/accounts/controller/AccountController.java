package ru.yandex.practicum.bankapp.accounts.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.bankapp.accounts.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{id}")
    public AccountResponse get(@PathVariable Long id) {
        return accountService.get(id);
    }

    @PostMapping
    public AccountResponse create(@RequestBody AccountRequest request) {
        return accountService.create(request);
    }
}
