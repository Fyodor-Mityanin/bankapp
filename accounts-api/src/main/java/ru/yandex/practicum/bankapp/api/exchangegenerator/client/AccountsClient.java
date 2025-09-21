package ru.yandex.practicum.bankapp.api.exchangegenerator.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.bankapp.api.exchangegenerator.api.CashRequestDto;

@FeignClient(
        name = "accountsClient",
        url = "${accounts.service.url:http://localhost:8081}"
)
public interface AccountsClient {

    @PostMapping("/api/v1/internal/cash/deposit")
    void deposit(@RequestBody CashRequestDto request);

    @PostMapping("/api/v1/internal/cash/withdraw")
    void withdraw(@RequestBody CashRequestDto request);
}