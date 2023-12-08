package com.ivan.wallet.service.impl;

import com.ivan.wallet.dao.AuditsDao;
import com.ivan.wallet.dao.TransactionsDao;
import com.ivan.wallet.dao.impl.AuditsDaoImpl;
import com.ivan.wallet.dao.impl.TransactionsDaoImpl;
import com.ivan.wallet.domain.Audits;
import com.ivan.wallet.domain.Transaction;
import com.ivan.wallet.domain.types.IdentifierType;
import com.ivan.wallet.domain.types.TransactionType;
import com.ivan.wallet.service.PlayerWalletService;
import com.ivan.wallet.service.TransactionWalletService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import static com.ivan.wallet.domain.types.ActionType.CREDIT_ACTION;
import static com.ivan.wallet.domain.types.ActionType.DEBIT_ACTION;
import static com.ivan.wallet.domain.types.IdentifierType.FAIL;
import static com.ivan.wallet.domain.types.IdentifierType.SUCCESS;
import static com.ivan.wallet.domain.types.TransactionType.CREDIT;
import static com.ivan.wallet.domain.types.TransactionType.DEBIT;

/**
 * The TransactionWalletServiceImpl class provides various operations related to player transactions and wallet management.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionWalletServiceImpl implements TransactionWalletService {
    private static final TransactionWalletServiceImpl INSTANCE = new TransactionWalletServiceImpl();

    /**
     * Get the singleton instance of TransactionWalletServiceImpl.
     *
     * @return The instance of TransactionWalletServiceImpl.
     */
    public static TransactionWalletServiceImpl getINSTANCE() {
        return INSTANCE;
    }

    AuditsDao<String, Audits> auditsDaoImpl = AuditsDaoImpl.getINSTANCE();
    TransactionsDao<String, Transaction> transactionsDaoImpl = TransactionsDaoImpl.getINSTANCE();
    PlayerWalletService playerWalletService = PlayerWalletServiceImpl.getINSTANCE();

    /**
     * Perform a debit transaction for the specified player with the given amount.
     *
     * @param username The username of the player.
     * @param amount   The amount to be debited.
     */
    @Override
    public void debit(String username, BigDecimal amount) {
        BigDecimal currentBalance = playerWalletService.currentPlayerBalance(username);

        if (currentBalance.compareTo(amount) < 0) {
            Audits audits = Audits.builder()
                    .playerName(username)
                    .actionType(DEBIT_ACTION)
                    .identifierType(FAIL)
                    .build();
            auditsDaoImpl.save(audits);

            System.out.println("Insufficient funds.");
        }

        BigDecimal result = currentBalance.subtract(amount);
        createTransaction(username, DEBIT, amount, SUCCESS);

        playerWalletService.updateBalance(username, result);

        Audits audits = Audits.builder()
                .playerName(username)
                .actionType(DEBIT_ACTION)
                .identifierType(SUCCESS)
                .build();
        auditsDaoImpl.save(audits);

        System.out.println("Дебетовая транзакция: " + amount + " списана со счёта игрока " + username);
    }

    /**
     * Perform a credit transaction for the specified player with the given amount.
     *
     * @param username The username of the player.
     * @param amount   The amount to be credited.
     */
    @Override
    public void credit(String username, BigDecimal amount) {
        BigDecimal currentBalance = playerWalletService.currentPlayerBalance(username);

        BigDecimal result = currentBalance.add(amount);
        createTransaction(username, CREDIT, amount, SUCCESS);

        playerWalletService.updateBalance(username, result);

        Audits audits = Audits.builder()
                .playerName(username)
                .actionType(CREDIT_ACTION)
                .identifierType(SUCCESS)
                .build();
        auditsDaoImpl.save(audits);

        System.out.println("Кредитная транзакция: " + amount + " добавлена на счет игрока " + username);
    }

    @Override
    public List<Transaction> showTransactionHistory(String username) {
        return transactionsDaoImpl.findByName(username);
    }

    /**
     * Create a new transaction and save it to the database.
     *
     * @param playerName     The name of the player.
     * @param type           The type of the transaction.
     * @param amount         The amount of the transaction.
     * @param identifierType The identifier type of the transaction.
     */
    public void createTransaction(String playerName, TransactionType type, BigDecimal amount, IdentifierType identifierType) {
        Transaction transaction = Transaction.builder() //этот метод нужно в директории маппер. и все ему подобные методы тоже
                .playerName(playerName)
                .type(type)
                .amount(amount)
                .identifierType(identifierType)
                .build();
        transactionsDaoImpl.save(transaction);
    }
}