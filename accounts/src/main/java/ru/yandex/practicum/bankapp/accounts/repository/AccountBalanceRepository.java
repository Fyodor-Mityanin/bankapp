package ru.yandex.practicum.bankapp.accounts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.bankapp.accounts.entity.AccountBalance;

@Repository
public interface AccountBalanceRepository extends JpaRepository<AccountBalance, Long> {
}
