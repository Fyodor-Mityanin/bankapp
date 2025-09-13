package ru.yandex.practicum.bankapp.exchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.bankapp.exchange.entity.Currency;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, String> {
}
