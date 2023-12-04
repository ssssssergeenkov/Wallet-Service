package com.ivan.wallet.domain;

import com.ivan.wallet.domain.types.IdentifierType;
import com.ivan.wallet.domain.types.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * The Transaction class represents a transaction entity.
 * It contains information about a transaction, including its ID, player name, type, amount, and identifier type.
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