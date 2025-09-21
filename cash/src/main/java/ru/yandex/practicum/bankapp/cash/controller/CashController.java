package ru.yandex.practicum.bankapp.cash.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.bankapp.api.accounts.api.CashRequestDto;
import ru.yandex.practicum.bankapp.cash.service.CashService;

@Slf4j
@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class CashController {

    private final CashService cashService;

    @PostMapping("/deposit")
    public void deposit(
            @RequestBody CashRequestDto requestDto
    ) {
        cashService.deposit(requestDto);
    }

    @PostMapping("/withdraw")
    public void withdraw(
            @RequestBody CashRequestDto requestDto
    ) {
        cashService.withdraw(requestDto);
    }
}
