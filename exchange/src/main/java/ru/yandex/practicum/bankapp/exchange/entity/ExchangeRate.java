package ru.yandex.practicum.bankapp.exchange.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "exchange_rates")
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "currency_from", nullable = false)
    private Currency currencyFrom;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "currency_to", nullable = false)
    private Currency currencyTo;

    @NotNull
    @Column(name = "rate", nullable = false, precision = 18, scale = 6)
    private BigDecimal rate;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
}
