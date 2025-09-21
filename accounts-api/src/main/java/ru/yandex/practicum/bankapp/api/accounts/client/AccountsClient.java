package ru.yandex.practicum.bankapp.api.accounts.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.bankapp.api.accounts.api.BalanceChangeRequestDto;

@FeignClient(
        name = "accountsClient",
        url = "${accounts.service.url:http://localhost:8081}"
)
public interface AccountsClient {

    @PostMapping("/api/v1/internal/balance/change")
    void changeBalance(@RequestBody BalanceChangeRequestDto request);
}