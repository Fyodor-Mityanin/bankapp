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

    @PostMapping("/self")
    public void selfTransfer(
            @RequestBody SelfTransferRequestDto requestDto
    ) {
        transferService.self(requestDto);
    }

    @PostMapping("/else")
    public void elseTransfer(
            @RequestBody ElseTransferRequestDto requestDto
    ) {
        transferService.elsee(requestDto);
    }

    public record SelfTransferRequestDto(
            String from, String to, String login, Double value
    ) {
    }

    public record ElseTransferRequestDto(
            String from, String to, String loginFrom, String loginTo, Double value
    ) {
    }
}
