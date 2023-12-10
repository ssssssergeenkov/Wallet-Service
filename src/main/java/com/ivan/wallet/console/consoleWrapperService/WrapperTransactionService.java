package com.ivan.wallet.console.consoleWrapperService;

import com.ivan.wallet.console.WalletConsole;
import com.ivan.wallet.domain.Transaction;
import com.ivan.wallet.service.impl.TransactionWalletServiceImpl;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * The WrapperTransactionService class provides wrapper methods for interacting with the TransactionService.
 * It handles user input and displays transaction-related information.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WrapperTransactionService {
    private static final WrapperTransactionService INSTANCE = new WrapperTransactionService();

    /**
     * Get the singleton instance of WrapperTransactionService.
     *
     * @return The instance of WrapperTransactionService.
     */
    public static WrapperTransactionService getINSTANCE() {
        return INSTANCE;
    }

    TransactionWalletServiceImpl transactionWalletServiceImpl = TransactionWalletServiceImpl.getINSTANCE();

    Scanner scanner = new Scanner(System.in);

    /**
     * Wrapper method for performing a debit transaction.
     *
     * @param player The player performing the transaction.
     */
    public void wrapperDebit(WalletConsole player) {
        System.out.print("Введите сумму дебетовой транзакции: ");
        BigDecimal debitAmount = BigDecimal.ZERO;
        try {
            debitAmount = scanner.nextBigDecimal();
        } catch (InputMismatchException e) {
            scanner.nextLine(); // Очистка входного буфера
        }
        transactionWalletServiceImpl.debit(player.getLoggedInUserName(), debitAmount);
    }

    /**
     * Wrapper method for performing a credit transaction.
     *
     * @param player The player performing the transaction.
     */
    public void wrapperCredit(WalletConsole player) {
        System.out.print("Введите сумму кредитной транзакции: ");
        BigDecimal creditAmount = BigDecimal.ZERO;
        try {
            creditAmount = scanner.nextBigDecimal();
        } catch (Exception e) {
            scanner.nextLine();
        }
        transactionWalletServiceImpl.credit(player.getLoggedInUserName(), creditAmount);
    }

    /**
     * Wrapper method for displaying the transaction history for a player.
     *
     * @param player The player for which to display the transaction history.
     */
    public void wrapperTransactionHistory(WalletConsole player) {
        List<Transaction> transactions = transactionWalletServiceImpl.showTransactionHistory(player.getLoggedInUserName());

        System.out.println("История транзакций для игрока " + player.getLoggedInUserName() + ":");
        System.out.println("―――――――――――――――――――――――――――――――――――――――――――――――――――――");
        System.out.printf("%-10s | %-12s | %-17s | %-14s | %s%n",
                "ID", "Player Name", "Transaction Type", "Amount", "Identifier Type");
        System.out.println("------------------------------------------------------------------------");
        for (Transaction transaction : transactions) {
            System.out.printf("%-10s | %-12s | %-17s | %-14.2f | %s%n",
                    transaction.getId(),
                    transaction.getPlayerName(),
                    transaction.getType(),
                    transaction.getAmount(),
                    transaction.getIdentifierType());
        }
        System.out.println("―――――――――――――――――――――――――――――――――――――――――――――――――――――");
    }
}