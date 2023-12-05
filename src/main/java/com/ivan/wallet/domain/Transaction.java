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
    /**
     * The id field represents the transaction ID.
     */
    private Integer id;

    /**
     * The playerName field represents the name of the player associated with the transaction.
     */
    private String playerName;

    /**
     * The type field represents the transaction type, such as debit or credit
     */
    private TransactionType type;

    /**
     * The amount field represents the transaction amount.
     */
    private BigDecimal amount;

    /**
     * The IDType field represents the type of ID associated with the transaction, such as fail or success
     */
    private IdentifierType identifierType;
}