package com.ivan.wallet.service;

import com.ivan.wallet.domain.Transaction;

import java.math.BigDecimal;
import java.util.List;

/**
 * Interface representing the wallet transaction service
 */
public interface TransactionWalletService {

    List<Transaction> showTransactionHistory(String username);

    /**
     * Debits the specified amount from the user's wallet.
     *
     * @param name   Name of the user from whom the amount should be debited.
     * @param amount The amount to write off.
     */
    void debit(String name, BigDecimal amount);

    /**
     * Credits the specified amount to the user's wallet.
     *
     * @param name   The name of the user whose wallet the amount should be credited to.
     * @param amount The amount to be credited.
     */
    void credit(String name, BigDecimal amount);
}