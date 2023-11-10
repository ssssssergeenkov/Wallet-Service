package com.ivan.wallet.domain;

import com.ivan.wallet.domain.types.IdentifierType;
import com.ivan.wallet.domain.types.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Класс Transaction представляет транзакцию с указанными свойствами.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class Transaction {
    private Integer id;
    private String playerName;
    private TransactionType type;
    private BigDecimal amount;
    private IdentifierType identifierType;
}