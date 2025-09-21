package ru.yandex.practicum.bankapp.transfer.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.bankapp.transfer.service.TransferService;

@Slf4j
@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping("/change")
    public void handleTransfer(
            @RequestBody TransferRequestDto requestDto
    ) {
        transferService.handle(requestDto);
    }

    public record TransferRequestDto(
            String from, String to, String login, Double value
    ) {
    }
}
