package ru.yandex.practicum.bankapp.exchangegenerator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.bankapp.api.exchange.api.ExchangeRateDto;
import ru.yandex.practicum.bankapp.api.exchange.client.ExchangeClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExchangeGeneratorService {

    private final ExchangeClient exchangeClient;
    private final Random random;

    @Scheduled(fixedRate = 1000)
    public void generateRates() {
        BigDecimal usdRate = randomDecimal(70, 80);
        BigDecimal cnyRate = randomDecimal(10, 15);

        sendRate("RUB", "USD", usdRate);
        sendRate("RUB", "CNY", cnyRate);
        sendRate("USD", "RUB", BigDecimal.ONE.divide(usdRate, 4, RoundingMode.HALF_UP));
        sendRate("CNY", "RUB", BigDecimal.ONE.divide(cnyRate, 4, RoundingMode.HALF_UP));
        sendRate("USD", "CNY", usdRate.divide(cnyRate, 4, RoundingMode.HALF_UP));
        sendRate("CNY", "USD", cnyRate.divide(usdRate, 4, RoundingMode.HALF_UP));
    }

    private BigDecimal randomDecimal(int min, int max) {
        double value = min + random.nextDouble() * (max - min);
        return BigDecimal.valueOf(value).setScale(4, RoundingMode.HALF_UP);
    }

    private void sendRate(String from, String to, BigDecimal rate) {
        log.info("Sending rate {} to {} = {}", from, to, rate);
        exchangeClient.sendRate(new ExchangeRateDto(from, to, rate));
    }
}
