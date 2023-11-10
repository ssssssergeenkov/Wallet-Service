package com.ivan.wallet.domain;

import com.ivan.wallet.domain.types.TransactionType;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Класс Transaction представляет транзакцию с указанными свойствами.
 *
 * @param id     идентификатор транзакции
 * @param type   тип транзакции (DEBIT - дебетовая, CREDIT - кредитная)
 * @param amount сумма транзакции
 */

public record Transaction(UUID id, TransactionType type, BigDecimal amount) {
}