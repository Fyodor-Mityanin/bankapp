package ru.yandex.practicum.bankapp.accounts.controller.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.bankapp.accounts.service.AccountService;
import ru.yandex.practicum.bankapp.api.accounts.api.BalanceChangeRequestDto;

@Slf4j
@RestController
@RequestMapping("api/v1/internal/balance")
@RequiredArgsConstructor
public class BalanceController {

    private final AccountService accountService;

    @PostMapping("/change")
    public void deposit(
            @RequestBody BalanceChangeRequestDto request
    ) {
        accountService.editBalance(request);
    }
}
