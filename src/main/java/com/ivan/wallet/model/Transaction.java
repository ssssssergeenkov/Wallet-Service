package com.ivan.wallet.model;

import com.ivan.wallet.model.types.TransactionType;

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