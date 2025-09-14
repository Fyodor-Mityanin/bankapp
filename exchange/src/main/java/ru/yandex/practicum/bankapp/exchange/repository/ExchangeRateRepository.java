package ru.yandex.practicum.bankapp.exchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.bankapp.exchange.entity.ExchangeRate;

import java.util.List;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Integer> {

    @Query("""
            SELECT er FROM ExchangeRate er
            WHERE er.currencyFrom.code = :currencyFrom
            AND er.createdAt = (
                SELECT MAX(e2.createdAt)
                FROM ExchangeRate e2
                WHERE e2.currencyFrom = er.currencyFrom
                  AND e2.currencyTo   = er.currencyTo
            )
            """)
    List<ExchangeRate> findLatestRates(String currencyFrom);
}
